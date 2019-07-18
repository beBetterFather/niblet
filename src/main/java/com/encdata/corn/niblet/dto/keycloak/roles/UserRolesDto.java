package com.encdata.corn.niblet.dto.keycloak.roles;

import java.util.List;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 用户公共角色和私有角色
 * @Author Siwei Jin
 * @Date 2018/10/25 15:01
 */
public class UserRolesDto {

    private List<String> publicRoles;

    private List<String> privateRoles;

    public List<String> getPublicRoles() {
        return publicRoles;
    }

    public void setPublicRoles(List<String> publicRoles) {
        this.publicRoles = publicRoles;
    }

    public List<String> getPrivateRoles() {
        return privateRoles;
    }

    public void setPrivateRoles(List<String> privateRoles) {
        this.privateRoles = privateRoles;
    }
}
