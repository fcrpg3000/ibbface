/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseController.java 2013-08-19 00:56
 */

package com.ibbface.controller;

import com.google.common.base.Joiner;
import com.ibbface.i18n.ResourceBundleMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Locale;

/**
 * SpringMVC 基础控制器。
 *
 * @author Fuchun
 * @version $Id: BaseController.java 30684 2013-05-28 11:29:25Z C629 $
 * @since 1.0
 */
public abstract class BaseController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认的404视图名称。
     */
    protected static final String PAGE_NOT_FOUND = "/404";
    /**
     * 默认的没有权限的视图。
     */
    protected static final String NO_PERMISSION = "/403";
    /**
     * 重定向视图名称字符串模版。
     */
    protected static final String REDIRECT_TPL = "redirect:%s";
    /**
     * 登录用户信息
     */
    protected static final String USER_INFO = "user";

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

//    @Resource
//    protected AppConfigExt appConfig;

    protected ResourceBundleMessageSource messageSource;

    /**
     * 返回重定向跳转至指定的视图的命令。
     *
     * @param viewName 视图名称。
     * @return 跳转命令。
     */
    protected String redirect(String viewName) {
        return String.format(REDIRECT_TPL, viewName);
    }

    /**
     * 返回重定向的模型与视图对象。
     *
     * @param viewName 视图名称。
     * @return 重定向的模型与视图对象。
     */
    protected ModelAndView redirectView(String viewName) {
        return new ModelAndView(String.format(REDIRECT_TPL, viewName));
    }

    protected ModelAndView view(String viewName, ModelMap model) {
        if (model == null) {
            model = new ModelMap();
        }

        final String viewPath = Joiner.on("").join("/", "default", viewName);
        return new ModelAndView(viewPath, model);
    }

    /**
     * 返回指定键的国际化资源信息。
     *
     * @param key    国际化资源Key。
     * @param params 参数（{0} {1} ...）。
     * @return 国际化资源信息。
     */
    protected String getText(String key, Object... params) {
        return messageSource.getMessage(key, params, new Locale(""));
    }

    @Resource
    public void setMessageSource(MessageSource messageSource) {
        if (messageSource instanceof ResourceBundleMessageSource) {
            this.messageSource = (ResourceBundleMessageSource) messageSource;
        } else if (messageSource instanceof DelegatingMessageSource) {
            this.messageSource = (ResourceBundleMessageSource) ((DelegatingMessageSource) messageSource).getParentMessageSource();
        }
    }
}
