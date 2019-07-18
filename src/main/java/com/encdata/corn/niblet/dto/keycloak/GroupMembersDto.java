package com.encdata.corn.niblet.dto.keycloak;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 机构用户信息类
 * @Author Siwei Jin
 * @Date 2018/10/25 13:34
 */
public class GroupMembersDto {

    //用户id
    private String id;

    //用户登录名
    private String username;

    //用户firstname
    private String firstName;

    //用户lastName
    private String lastName;

    //用户中文名
    private String cnName;

    //用户是否启用
    private Boolean enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }
}
