package com.pd.security.model;

import com.pd.security.base.model.DataModel;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author peramdy on 2018/6/2.
 *         菜单实体
 */
public class Menu extends DataModel<Menu> {

    /**
     * 父菜单Id
     */
    private Long parentId;
    /**
     * 父菜单Ids
     */
    private String parentIds;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单跳转链接
     */
    private String href;
    /**
     * 权限标识
     */
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
