package com.pd.security.model;

import com.pd.security.base.model.DataModel;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author peramdy on 2018/6/2.
 */
public class Menu extends DataModel<Menu> {

    private Long parentId;
    private String parentIds;
    private String name;
    private String href;
    private String permission;


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
