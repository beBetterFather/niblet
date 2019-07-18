package com.encdata.corn.niblet.dto.keycloak;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 部门列表接口返回list中单个元素对象
 * @Author Siwei Jin
 * @Date 2018/10/25 9:13
 */
public class GroupElementDto {

    //机构id
    private String id;

    //机构名称
    private String name;

    //父机构id
    private String pId;

    //父机构名称
    private String pName;

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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
