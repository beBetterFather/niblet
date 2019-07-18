package com.encdata.corn.niblet.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 登出
 * @Author Siwei Jin
 * @Date 2018/10/13 11:28
 */
@Component
public class UrlLogoutHandler implements LogoutHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UrlLogoutHandler.class);

    @Override
    public void logout(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , Authentication authentication) {
        //退出可以做到操作，如记录日志等
    }
}
