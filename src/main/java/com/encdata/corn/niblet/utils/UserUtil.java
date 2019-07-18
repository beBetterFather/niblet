package com.encdata.corn.niblet.utils;

import com.encdata.corn.niblet.domain.Role;
import com.encdata.corn.niblet.domain.User;
import com.encdata.corn.niblet.dto.keycloak.KeycloakGroupExt;
import com.encdata.corn.niblet.dto.keycloak.roles.UseRoleSimpleDto;
import com.encdata.corn.niblet.enums.RolesEnum;
import com.encdata.corn.niblet.service.intf.KeycloakProxy;
import com.enn.core.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;

//用户公共类
public class UserUtil {

    private static final KeycloakProxy keycloakProxy = ApplicationContextRegister.getBean(KeycloakProxy.class);

    //获取当前登录用户登录名
    public static User getCurrentUser(){
        User user = new User();
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(null!=keycloakPrincipal){
            processUserInfo(user, keycloakPrincipal.getKeycloakSecurityContext().getToken());
        }
        List<KeycloakGroupExt> groups= keycloakProxy.getUserGroupInfo(user.getId());
        user.setGroups(groups);
        return user;
    }

    //获取当前登录用户登录名
    public static UseRoleSimpleDto getUserRoles(){
        UseRoleSimpleDto dto = new UseRoleSimpleDto();
        List<Role> list = Lists.newArrayList();

        User user = getCurrentUser();
        Set<String> roles = user.getRealmRoles();
        for(String role : roles){
            Role r = new Role();
            r.setEnName(role);
            if(StringUtils.isNotEmpty(RolesEnum.readKey(role))){
                r.setChName(RolesEnum.readKey(role));
            }
            list.add(r);
        }
        dto.setRoles(list);
        return dto;
    }

    //登录人是否是超级管理员
    public static boolean isAdmin(){
        User user = getCurrentUser();
        Set<String> roles = user.getRealmRoles();
        for(String role : roles){
            if(RolesEnum.isAdmin(role)){
                return true;
            }
        }
        return false;
    }

    //获取当前登录用户角色信息
    public static Set<String> getAuthorities(){
        Set<String> roles = Sets.newHashSet();
        List<KeycloakRole> authorities = (List<KeycloakRole>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(KeycloakRole keycloakRole:authorities){
            roles.add(keycloakRole.getAuthority());
        }
        return roles;
    }

    /**
     * @Description 将token转换成user
     * @since JDK 1.8
     * @author Siwei Jin
     * @Date 2018/10/16 11:31
     */
    private static User processUserInfo(User user, AccessToken token){
        //realmRole信息
        user.setRealmRoles(token.getRealmAccess().getRoles());
        //clientRole信息
        user.setClientRoles(UserUtil.getAuthorities());
        //用户中文名
        user.setCnname(token.getName());
        //用户登录名
        user.setUsername(token.getPreferredUsername());
        //用户邮件
        user.setEmail(token.getEmail());
        //用户id
        user.setId(token.getSubject());

        return user;
    }
}
