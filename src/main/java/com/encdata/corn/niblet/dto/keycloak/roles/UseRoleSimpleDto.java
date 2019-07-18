package com.encdata.corn.niblet.dto.keycloak.roles;

import com.encdata.corn.niblet.domain.Role;

import java.util.List;

/**
 * @Author: jinsi
 * @Date: 2019/7/16 17:29
 * @Description:
 */
public class UseRoleSimpleDto {

    List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
