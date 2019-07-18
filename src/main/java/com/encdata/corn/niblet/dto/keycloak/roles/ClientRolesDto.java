package com.encdata.corn.niblet.dto.keycloak.roles;

import java.util.Map;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description keycloak私有角色
 * @Author Siwei Jin
 * @Date 2018/10/25 15:23
 */
public class ClientRolesDto {

    private Map<String, KeycloakClientRoleElementDto> clientMappings;

    public Map<String, KeycloakClientRoleElementDto> getClientMappings() {
        return clientMappings;
    }

    public void setClientMappings(Map<String, KeycloakClientRoleElementDto> clientMappings) {
        this.clientMappings = clientMappings;
    }
}
