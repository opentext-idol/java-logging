package com.autonomy.frontend.logging;

/*
 * $Id: $
 *
 * Copyright (c) 2014, Autonomy Systems Ltd.
 *
 * Last modified by $Author: $ on $Date: $
 */

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

public class UserLoggingFilter implements Filter {

    private static final String DEFAULT_IP_KEY = "ip";
    private static final String DEFAULT_USER_KEY = "username";
    private static final String DEFAULT_USER_SESSION_ATTRIBUTE = "username";

    private String ipKey;
    private String userKey;
    private String userSessionAttribute;

    @Override
    public void destroy() {}

    @Override
    public void init(final FilterConfig config) throws ServletException {
        this.ipKey = config.getInitParameter("ipKey");
        this.userKey = config.getInitParameter("userKey");
        this.userSessionAttribute = config.getInitParameter("userSessionAttribute");

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

        MDC.put(userKey, (String) session.getAttribute(userSessionAttribute));
        MDC.put(ipKey, httpRequest.getRemoteAddr());

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(userKey);
            MDC.remove(ipKey);
        }
    }

}
