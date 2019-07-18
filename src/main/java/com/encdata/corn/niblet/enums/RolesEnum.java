package com.encdata.corn.niblet.enums;

import com.encdata.corn.niblet.domain.Role;

/**
 * @Author: jinsi
 * @Date: 2019/7/16 17:18
 * @Description:
 */
public enum RolesEnum {

    ADMIN("ROLE_PUBLIC_ADMIN", "超级管理员"),
    VISITOR("ROLE_PUBLIC_VISITOR", "访客");

    private String key;

    private String value;

    RolesEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public static String readKey(String key){
        for(RolesEnum role : values()){
            if(key.equals(role.key)){
                return role.value;
            }
        }
        return "";
    }

    public static boolean isAdmin(String key){
        if(RolesEnum.ADMIN.key.equals(key)){
            return true;
        }else{
            return false;
        }
    }

}
