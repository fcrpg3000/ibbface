/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) OAuthController.java 2013-08-29 13:39
 */

package com.ibbface.controller;

import com.ibbface.context.AppContext;
import com.ibbface.domain.model.client.AppClient;
import com.ibbface.domain.model.client.ClientInfo;
import com.ibbface.interfaces.oauth.OAuthParameter;
import com.ibbface.interfaces.resp.ErrorResponse;
import com.ibbface.service.AppClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.ibbface.interfaces.resp.ErrorCodes.INVALID_CLIENT;
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

        model.put("oauth", oAuthParam);
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
}
