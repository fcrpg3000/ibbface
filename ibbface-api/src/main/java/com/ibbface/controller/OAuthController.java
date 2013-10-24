/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) OAuthController.java 2013-08-29 13:39
 */

package com.ibbface.controller;

import com.google.common.base.Joiner;
import com.ibbface.context.AppContext;
import com.ibbface.domain.model.client.AppClient;
import com.ibbface.domain.model.client.ClientInfo;
import com.ibbface.interfaces.oauth.OAuthAccountPasswordToken;
import com.ibbface.interfaces.oauth.OAuthParameter;
import com.ibbface.interfaces.resp.ErrorCode;
import com.ibbface.interfaces.resp.ErrorResponse;
import com.ibbface.service.AppClientService;
import com.ibbface.service.OAuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.ibbface.interfaces.resp.ErrorCode.USER_NOT_FOUND;
import static com.ibbface.interfaces.resp.ErrorCodes.*;
import static com.ibbface.interfaces.resp.ErrorResponses.byCode;

/**
 * @author Fuchun
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/oauth")
public class OAuthController extends BaseController {
    private static final long serialVersionUID = 1L;

    private AppClientService appClientService;
    private OAuthService oAuthService;

    /**
     * 载入 oauth 登录页面。
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ModelAndView authorize(HttpServletRequest request, ModelMap model) {
        final OAuthParameter oAuthParam = OAuthParameter.fromRequest(request);
        final String requestURI = request.getRequestURI();
        ErrorResponse errorResponse = oAuthParam.validateAuthorizeRequest(getValidation(), requestURI);

        if (errorResponse == null) {
            if (!appClientService.isValid(oAuthParam.getIntClientId())) {
                errorResponse = byCode(INVALID_CLIENT, requestURI);
            }
        }
        // authorize parameters error
        if (errorResponse != null) {
            model.put("errorResponse", errorResponse);
            return view("/auth/authorize_error.ftl", model);
        }

        model.put("oauth", oAuthParam);
        return view("/auth/authorize.ftl", model);
    }

//    /**
//     * 处理用户认证，并跳转至用户指定的URI（IBBFace的clientId不需要指定redirect_uri）
//     */
//    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
//    public ModelAndView doAuthorize(HttpServletRequest request, ModelMap model) {
//        AuthenticationException authEx;
//        String requestURI = request.getRequestURI();
//        OAuthAccountPasswordToken oAuthToken = (OAuthAccountPasswordToken) request.getAttribute("token");
//        OAuthParameter oAuthParam = oAuthToken == null ? OAuthParameter.fromRequest(request) : oAuthToken.getoAuthParameter();
//        assert oAuthParam != null;
//        ErrorResponse errorResponse = oAuthParam.validateAuthorizeRequest(getValidation(), requestURI);
//        if (errorResponse == null) {
//            if (!appClientService.isValid(oAuthParam.getIntClientId())) {
//                errorResponse = byCode(INVALID_CLIENT, requestURI);
//            }
//        }
//        if ((authEx = (AuthenticationException) request.getAttribute("loginFailureError")) != null) {
//            // user was locked
//            ErrorCode errorCode;
//            if (authEx instanceof LockedAccountException) {
//                errorCode = INVALID_USER;
//            } else if (authEx instanceof UnknownAccountException) {
//                errorCode = findByCode(USER_NOT_FOUND);
//            } else {
//                errorCode = findByCode(authEx.getMessage());
//            }
//            model.put("errorResponse", byCode(errorCode, requestURI));
//            return view("/auth/authorize.ftl", model);
//        }
//        String redirectUri = oAuthParam.getRedirectUri();
//        return redirectView(redirectUri);
//    }

    /**
     * 处理用户认证，并跳转至用户指定的URI。
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView doAuthorize(HttpServletRequest request, ModelMap model) {
        final String requestURI = request.getRequestURI();
        OAuthAccountPasswordToken oAuthToken = (OAuthAccountPasswordToken) request.getAttribute("token");
        ErrorResponse errorResponse;
        if (oAuthToken == null) {
            errorResponse = byCode(INVALID_REQUEST, requestURI);
        } else {
            errorResponse = oAuthToken.validateDoAuthorize(getValidation(), requestURI);
        }

        if (errorResponse != null) {
            model.put("errorResponse", errorResponse);
            return view("/auth/authorize_error.ftl", model);
        }

        OAuthParameter oAuthParam = oAuthToken.getoAuthParameter();
        assert oAuthParam != null;

        Subject subject;
        try {
            subject = SecurityUtils.getSubject();
            subject.login(oAuthToken);
        } catch (AuthenticationException ex) {
            ErrorCode errorCode;
            if (ex instanceof LockedAccountException) {
                errorCode = INVALID_USER;
            } else if (ex instanceof UnknownAccountException) {
                errorCode = findByCode(USER_NOT_FOUND);
            } else {
                errorCode = findByCode(ex.getMessage());
            }
            model.put("errorResponse", byCode(errorCode, requestURI));
            return view("/auth/authorize.ftl", model);
        }
        // login success, redirect user specified uri
        String authCode = oAuthService.completeLogin(subject, oAuthToken);
        final String redirectUri = Joiner.on("").join(oAuthParam.getRedirectUri(),
                "?code=", authCode, (oAuthParam.getState() == null ? "" : "&state=" + oAuthParam.getState()));
        return redirectView(redirectUri);
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

        final AppClient appClient = appClientService.getAppClient(oAuthParam.getIntClientId());
        final ClientInfo clientInfo = AppContext.getClientInfo();
        // app client not found, or secret and version mismatch.
        if (appClient == null || !appClient.isValid(oAuthParam.getClientSecret(), clientInfo)) {
            return byCode(INVALID_CLIENT, requestURI).toJSONString();
        }
        return null;
    }

    @Resource
    public void setAppClientService(AppClientService appClientService) {
        this.appClientService = appClientService;
    }

    @Resource
    public void setOAuthService(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }
}