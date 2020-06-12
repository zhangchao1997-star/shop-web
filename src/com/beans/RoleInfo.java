package com.beans;

/**
 * author zhangchao
 * 角色信息实体类
 */
public class RoleInfo {
    private Integer id; //角色id
    private String roleName; //角色名称
    private String des; //角色描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
