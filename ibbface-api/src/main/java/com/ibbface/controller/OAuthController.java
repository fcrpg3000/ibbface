/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) OAuthController.java 2013-08-29 13:39
 */

package com.ibbface.controller;

import com.ibbface.interfaces.oauth.OAuthParameter;
import com.ibbface.interfaces.resp.ErrorResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Fuchun
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/oauth")
public class OAuthController extends BaseController {
    private static final long serialVersionUID = 1L;

    /**
     * 载入 oauth 登录页面。
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ModelAndView authorize(HttpServletRequest request, ModelMap model) {
        final OAuthParameter oAuthParam = OAuthParameter.fromRequest(request);

        // authorize parameters error
        if (oAuthParam.getClientId() == null || oAuthParam.getRedirectUri() == null) {
            return view("/auth/authorize_error.ftl", model);
        }

        return view("/auth/authorize.ftl", model);
    }

    /**
     * 处理用户认证，并跳转至用户指定的URI（IBBFace的clientId不需要指定redirect_uri）
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView authorize() {

        return null;
    }

    @RequestMapping(value = "/access_token", method = RequestMethod.POST)
    @ResponseBody
    public String getAccessToken(HttpServletRequest request) {
        final OAuthParameter oAuthParam = OAuthParameter.fromRequest(request);
        final String requestURI = request.getRequestURI();
        ErrorResponse errorResponse = oAuthParam.validateAccessTokenRequest(
                getValidation(), requestURI);

        if (errorResponse != null) {
            return errorResponse.toJSONString();
        }
        return null;
    }
}
