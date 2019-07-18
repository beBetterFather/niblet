package com.encdata.corn.niblet.dto.keycloak;

import java.util.List;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 按照pap的部门列表接口返回格式分对象
 * @Author Siwei Jin
 * @Date 2018/10/25 9:12
 */
public class GroupListDto {

    private List<GroupElementDto> list;

    public List<GroupElementDto> getList() {
        return list;
    }

    public void setList(List<GroupElementDto> list) {
        this.list = list;
    }
}
