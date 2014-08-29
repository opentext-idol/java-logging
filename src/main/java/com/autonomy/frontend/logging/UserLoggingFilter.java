/*
 * Copyright 2014 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.autonomy.frontend.logging;

import java.security.Principal;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
public class UserLoggingFilter implements Filter {

    private static final String DEFAULT_IP_KEY = "ip";
    private static final String DEFAULT_USER_KEY = "username";
    private static final String DEFAULT_USER_SESSION_ATTRIBUTE = "username";

    private String ipKey;
    private String userKey;
    private String userSessionAttribute;
    private boolean usePrincipal;

    @Override
    public void destroy() {}

    @Override
    public void init(final FilterConfig config) throws ServletException {
        this.ipKey = config.getInitParameter("ipKey");
        this.userKey = config.getInitParameter("userKey");
        this.userSessionAttribute = config.getInitParameter("userSessionAttribute");
        this.usePrincipal = Boolean.valueOf(config.getInitParameter("usePrincipal"));

        if (this.ipKey  == null) {
            this.ipKey = DEFAULT_IP_KEY;
        }

        if (this.userKey == null) {
            this.userKey = DEFAULT_USER_KEY;
        }

        if (this.userSessionAttribute == null) {
            this.userSessionAttribute = DEFAULT_USER_SESSION_ATTRIBUTE;
        }
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpSession session = httpRequest.getSession();
        final String username;

        if(usePrincipal) {
            final Principal principal = httpRequest.getUserPrincipal();

            if(principal != null) {
                username = principal.getName();
            }
            else {
                username = null;
            }
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
