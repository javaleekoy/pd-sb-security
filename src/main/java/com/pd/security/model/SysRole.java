package com.pd.security.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author peramdy on 2018/5/18.
 */
public class SysRole implements Serializable {

    private Integer id;
    private String role;
    private List<String> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}
