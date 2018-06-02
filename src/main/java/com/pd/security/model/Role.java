package com.pd.security.model;

import com.pd.security.base.model.DataModel;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author peramdy on 2018/6/2.
 */
public class Role extends DataModel<Role> {

    private String name;
    private Integer roleType;
    private Integer disable;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
