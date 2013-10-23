package com.ibbface.interfaces.web;

import com.ibbface.controller.ClearAware;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Application global interceptor all request and response.
 *
 * @author Fuchun
 * @since 1.0
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        if (handler instanceof ClearAware) {
            ((ClearAware) handler).clear();
        }
    }
}
