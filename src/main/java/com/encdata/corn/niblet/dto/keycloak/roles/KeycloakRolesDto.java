package com.encdata.corn.niblet.dto.keycloak.roles;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description 用户所有角色
 * @Author Siwei Jin
 * @Date 2018/10/25 15:22
 */
public class KeycloakRolesDto {

    private List<PublicRolesDto> realmMappings;

    private Map<String, KeycloakClientRoleElementDto> clientMappings;

    public List<PublicRolesDto> getRealmMappings() {
        return realmMappings;
    }

    public void setRealmMappings(List<PublicRolesDto> realmMappings) {
        this.realmMappings = realmMappings;
    }

    public Map<String, KeycloakClientRoleElementDto> getClientMappings() {
        return clientMappings;
    }

    public void setClientMappings(Map<String, KeycloakClientRoleElementDto> clientMappings) {
        this.clientMappings = clientMappings;
    }
}
