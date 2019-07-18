package com.encdata.corn.niblet.dto.keycloak;

import java.util.List;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description keycloak group信息返回封装类集合
 * @Author Siwei Jin
 * @Date 2018/10/16 23:17
 */
public class KeycloakGroupsExt {

    private List<KeycloakGroupExt> groups;

    public List<KeycloakGroupExt> getGroups() {
        return groups;
    }

    public void setGroups(List<KeycloakGroupExt> groups) {
        this.groups = groups;
    }
}
