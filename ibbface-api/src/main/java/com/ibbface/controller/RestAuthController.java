/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) RestAuthController.java 2013-09-09 15:13
 */

package com.ibbface.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.ibbface.interfaces.resp.ErrorResponses.byError;

/**
 * @author Fuchun
 * @since 1.0
 * @deprecated use {@link OAuthController}
 */
@Controller
@RequestMapping("/auth")
@Deprecated
public class RestAuthController extends BaseController {
    private static final long serialVersionUID = 1L;

    @RequestMapping(value = "/authorize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String authorize(HttpServletRequest request) {
        AuthenticationException authEx = (AuthenticationException) request.getAttribute("auth_exception");
        if (authEx != null) {
            String requestUri = WebUtils.getRequestUri(request);
            return byError(authEx.getMessage(), requestUri, null).toJSONString();
        }
        AuthenticationToken token = (AuthenticationToken) request.getAttribute("authToken");
        Subject subject = (Subject) request.getAttribute("subject");
        return "{\"access_token\":\"test\"}";
    }
}
