/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) OAuthController.java 2013-08-29 13:39
 */

package com.ibbface.controller;

import com.ibbface.web.ServletUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    private String getClientId(final HttpServletRequest request) {
        return ServletUtils.getParameter(request, "client_id");
    }

    /**
     * 载入 oauth 登录页面。
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ModelAndView authorize(HttpServletRequest request, ModelMap model) {
        final String clientId = getClientId(request);
        final String responseType = ServletUtils.getParameter(request, "response_type");
        final String redirectUri = ServletUtils.getParameter(request, "redirect_uri");
        final String state = ServletUtils.getParameter(request, "state");

        if (clientId.length() == 0 || redirectUri.length() == 0) { // authorize parameters error
            return view("/auth/authorize_error.ftl", model);
        }
        model.put("clientId", clientId);
        model.put("responseType", responseType);
        model.put("redirectUri", redirectUri);
        model.put("state", state);

        return view("/auth/authorize.ftl", model);
    }

    /**
     * 处理用户认证，并跳转至用户指定的URI（IBBFace的clientId不需要指定redirect_uri）
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView authorize() {

        return null;
    }
}
