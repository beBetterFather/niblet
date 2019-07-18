package com.encdata.corn.niblet.dto.keycloak;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description keycloak group信息返回封装类集合元素
 * @Author Siwei Jin
 * @Date 2018/10/16 16:13
 */
public class KeycloakGroupExt {

    private String id;

    private String name;

    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
