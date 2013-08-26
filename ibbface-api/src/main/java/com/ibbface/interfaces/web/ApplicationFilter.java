/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ApplicationFilter.java 2013-08-24 13:41
 */

package com.ibbface.interfaces.web;

import com.google.common.base.Charsets;
import com.ibbface.context.AppContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * The IBB face API application filter implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ApplicationFilter implements Filter {

    private static final Pattern DEFAULT_STATICS_PATTERN =
            Pattern.compile("\\.(jpg|jpeg|png|gif|ico|css|js)\\s*$");

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String requestURI = request.getRequestURI();
        if (DEFAULT_STATICS_PATTERN.matcher(requestURI).matches()) {
            chain.doFilter(request, response);
            return;
        }

        // Redirect requests with JSESSIONID in URL to clean version (old links
        // bookmarked/stored by bots). The is ONLY triggered if the request did
        // not also contain a JSESSIONID cookie! Which should be fine for bots
        if (request.isRequestedSessionIdFromURL()) {
            String url = request.getRequestURL()
                    .append(request.getQueryString() != null ? "?" + request.getQueryString() : "")
                    .toString();
            response.setHeader("Location", url);
            response.sendError(HttpServletResponse.SC_MOVED_PERMANENTLY);
            return;
        }

        request.setCharacterEncoding(Charsets.UTF_8.displayName());
        response.setCharacterEncoding(Charsets.UTF_8.displayName());

        // Prevent rendering of JSESSIONID in URLs for all outgoing links
        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(response) {
            @Override
            public String encodeRedirectUrl(String url) {
                return url;
            }

            @Override
            public String encodeRedirectURL(String url) {
                return url;
            }

            @Override
            public String encodeUrl(String url) {
                return encodeURL(url);
            }

            @Override
            public String encodeURL(String url) {
                return url;
            }
        };

        AppContext.initAppContext(request);

        try {
            chain.doFilter(request, wrappedResponse);
        } finally {
            AppContext.reset();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }
}
