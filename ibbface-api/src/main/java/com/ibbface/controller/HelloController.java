/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) HelloController.java 2013-08-19 00:57
 */

package com.ibbface.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Fuchun
 * @since 1.0
 */
@RequestMapping
@Controller
public class HelloController extends BaseController {

    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "HelloWorld";
    }
}
