/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) SecurityInterceptor.java 2013-09-02 23:10
 */

package com.ibbface.web;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Fuchun
 * @since 1.0
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);
    private static final Pattern STATIC_PATTERN = Pattern.compile(
            "(\\.(?:jpg|jpeg|png|gif|ico|js|css|xml))\\s*$");
    private static final Pattern URI_PATTERN = Pattern.compile("^/v(\\d+)(/.*?)");
    private static final Function<String, Pattern> STRING_PATTERN_FUNCTION =
            new Function<String, Pattern>() {
                @Override
                public Pattern apply(String input) {
                    return Pattern.compile(input.trim());
                }
            };

    private List<Pattern> anonymousPattern = null;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        final String requestURI = request.getRequestURI();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The security preHandle to request uri: {}", requestURI);
        }
        if (STATIC_PATTERN.matcher(requestURI).find()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Pass all static resources");
            }
            return true;
        }
        if (anonymousPattern != null) {
            for (Pattern p : anonymousPattern) {
                if (p.matcher(requestURI).matches()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Anonymous pattern ({}) excludes this request: {}",
                                p.pattern(), requestURI);
                    }
                    return true;
                }
            }
        }
        // 指定了API的版本
        Matcher uriMatcher = URI_PATTERN.matcher(requestURI);
        String version, basePath;
        if (uriMatcher.find()) {
            version = uriMatcher.group(1);
            basePath = uriMatcher.group(2);
        } else {
            version = "v1";
            basePath = requestURI;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The api resource (version: {}, basePath: {})", version, basePath);
        }
        return true;
    }

    public void setAnonymousList(List<String> anonymous) {
        if (anonymous == null || anonymous.isEmpty()) {
            return;
        }
        Iterable<String> realExcludes = Iterables.filter(anonymous, Predicates.notNull());
        Iterable<Pattern> patternIterable = Iterables.transform(realExcludes, STRING_PATTERN_FUNCTION);
        this.anonymousPattern = Lists.newArrayList(patternIterable);
    }
}
