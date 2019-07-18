package com.encdata.corn.niblet.service.intf;

import com.encdata.corn.niblet.dto.keycloak.GroupElementDto;
import com.encdata.corn.niblet.dto.keycloak.GroupMembersDto;
import com.encdata.corn.niblet.dto.keycloak.KeycloakGroupExt;
import com.encdata.corn.niblet.dto.keycloak.roles.UserRolesDto;

import java.util.List;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description keycloak接口
 * @Author Siwei Jin
 * @Date 2018/10/16 15:58
 */
public interface KeycloakProxy {

    //获取token
    String getAccessToken();

    //根据用户id获取对应的机构信息
    List<KeycloakGroupExt> getUserGroupInfo(String userId);

    //获取所有机构信息
    List<GroupElementDto> getGroups();

    //根据机构id获取该机构所有用户
    List<GroupMembersDto> getGroupmembers(String groupId);

    //获取用户的公共角色
    UserRolesDto getUserRoles(String userId, String clientId);
}
