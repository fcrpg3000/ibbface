/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) SecurityInterceptorTest.java 2013-09-02 23:57
 */

package com.ibbface.web;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertTrue;

/**
 * @author Fuchun
 * @since 1.0
 */
public class SecurityInterceptorMockTest {

    private SecurityInterceptor interceptor;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        interceptor = new SecurityInterceptor();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setCharacterEncoding(Charsets.UTF_8.displayName());
        request.setContentType("application/json;charset=UTF-8");
        request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Charsets.UTF_8.displayName());
    }

    @Test
    public void testPreHandleWithStatics() throws Exception {
        request.setRequestURI("/images/logo.png");
        request.setContentType(MediaType.IMAGE_PNG_VALUE);
        assertTrue(interceptor.preHandle(request, response, new Object()));
    }

    @Test
    public void testPreHandleWithExcludes() throws Exception {
        request.setRequestURI("/oauth2/authorize");
        interceptor.setAnonymousList(Lists.newArrayList("/oauth2/authorize"));
        assertTrue(interceptor.preHandle(request, response, new Object()));
    }
}
