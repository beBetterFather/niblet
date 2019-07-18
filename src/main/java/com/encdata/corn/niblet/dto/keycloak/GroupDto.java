package com.encdata.corn.niblet.dto.keycloak;

import java.util.List;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 机构类
 * @Author Siwei Jin
 * @Date 2018/10/24 18:00
 */
public class GroupDto {

    //机构id
    private String id;

    //机构名称
    private String name;

    //机构路径
    private String path;

    //下级机构
    private List<GroupDto> subGroups;

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

    public List<GroupDto> getSubGroups() {
        return subGroups;
    }

    public void setSubGroups(List<GroupDto> subGroups) {
        this.subGroups = subGroups;
    }
}
