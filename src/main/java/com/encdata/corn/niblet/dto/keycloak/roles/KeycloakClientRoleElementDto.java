package com.encdata.corn.niblet.dto.keycloak.roles;

import java.util.List;

/**
 * Copyright (c) 2015-2017 Enc Group
 *
 * @Description keycloak私有角色元素
 * @Author Siwei Jin
 * @Date 2018/10/25 15:48
 */
public class KeycloakClientRoleElementDto {

    private String id;

    private String client;

    private List<PublicRolesDto> mappings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<PublicRolesDto> getMappings() {
        return mappings;
    }

    public void setMappings(List<PublicRolesDto> mappings) {
        this.mappings = mappings;
    }
}
