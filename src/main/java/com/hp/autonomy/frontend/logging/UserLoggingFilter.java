/*
 * Copyright 2015 Open Text.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Open Text and its affiliates
 * and licensors ("Open Text") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Open Text shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
 */

package com.hp.autonomy.frontend.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.io.IOException;
import java.security.Principal;

/**
 * {@link Filter} implementation that adds the user's IP address and username to the MDC logging context
 *
 * The filter takes the following init params:
 *
 * <dl>
 *     <dt>ipKey</dt><dd>The key under which the IP address will be added to the MDC context. Defaults to "ip"</dd>
 *     <dt>userKey</dt><dd>The key under which the username will be added to the MDC context. Defaults to "username"</dd>
 *     <dt>usePrincipal</dt><dd>Indicates that the username will be read from the security principal rather than directly from the session.  Defaults to false</dd>
 *     <dt>userSessionAttribute</dt><dd>The session attribute from which the username will be read.  Has no effect if usePrincipal is set to true. Defaults to "username"</dd>
 * </dl>
 */
@Data
public class UserLoggingFilter implements Filter {

    private static final String DEFAULT_IP_KEY = "ip";
    private static final String DEFAULT_USER_KEY = "username";
    private static final String DEFAULT_USER_SESSION_ATTRIBUTE = "username";

    private String ipKey = DEFAULT_IP_KEY;
    private String userKey = DEFAULT_USER_KEY;
    private String userSessionAttribute = DEFAULT_USER_SESSION_ATTRIBUTE;
    private boolean usePrincipal;

    @Override
    public void destroy() {}

    @Override
    public void init(final FilterConfig config) throws ServletException {
        ipKey = StringUtils.defaultIfBlank(config.getInitParameter("ipKey"), ipKey);
        userKey = StringUtils.defaultIfBlank(config.getInitParameter("userKey"), userKey);
        userSessionAttribute = StringUtils.defaultIfBlank(config.getInitParameter("userSessionAttribute"), userSessionAttribute);
        usePrincipal = BooleanUtils.toBooleanDefaultIfNull(BooleanUtils.toBooleanObject(config.getInitParameter("usePrincipal")), usePrincipal);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpSession session = httpRequest.getSession();
        final String username;

        if(usePrincipal) {
            final Principal principal = httpRequest.getUserPrincipal();
            username = principal != null ? principal.getName() : null;
        }
        else {
            username = (String) session.getAttribute(userSessionAttribute);
        }

        MDC.put(userKey, username);
        MDC.put(ipKey, httpRequest.getRemoteAddr());

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(userKey);
            MDC.remove(ipKey);
        }
    }

}
