package com.encdata.corn.niblet.dto.keycloak.roles;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 用户公共角色
 * @Author Siwei Jin
 * @Date 2018/10/25 14:57
 */
public class PublicRolesDto {

    //id
    private String id;

    //英文
    private String name;

    //中文名
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
