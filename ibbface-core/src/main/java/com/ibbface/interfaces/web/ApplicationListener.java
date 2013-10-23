/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ApplicationListener.java 2013-08-19 00:46
 */

package com.ibbface.interfaces.web;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * @author Fuchun
 * @since 1.0
 */
public class ApplicationListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
    }
}
