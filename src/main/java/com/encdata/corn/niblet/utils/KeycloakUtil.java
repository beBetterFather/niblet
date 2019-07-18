package com.encdata.corn.niblet.utils;

import com.encdata.corn.niblet.service.intf.KeycloakProxy;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description Keycloak工具类 如修改请联系作者
 * @Author Siwei Jin
 * @Date 2018/10/17 17:15
 */
public class KeycloakUtil {

    private static final Logger LOG = LoggerFactory.getLogger(KeycloakUtil.class);

    private static final KeycloakProxy proxy = ApplicationContextRegister.getBean(KeycloakProxy.class);

    /**
      * @Description 获取keycloak部署相关信息
      * @since JDK 1.8
      * @author Siwei Jin
      * @Date 2018/10/17 17:19
      */
    public static KeycloakDeployment getDeployment(){
        try{
            KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            RefreshableKeycloakSecurityContext context = (RefreshableKeycloakSecurityContext)keycloakPrincipal.getKeycloakSecurityContext();
            return context.getDeployment();

        }catch (Exception e){
            LOG.error("getDeployment ERROR {}", e);
        }
        return null;
    }


    /**
      * @Description 獲取當前用戶的token
      * @since JDK 1.8
      * @author Siwei Jin
      * @Date 2018/11/2 9:26
      */
    public static String getCurrentToken(){
        String resp = null;
        //1、首先尝试获取当前登录人token
        try{
            KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            resp =  keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
        }catch (Exception e){
            LOG.error("获取当前登录人的token失败 {}", e);
            resp = proxy.getAccessToken();
        }
        return resp;
    }
}
