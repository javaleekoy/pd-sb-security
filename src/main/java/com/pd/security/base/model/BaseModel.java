package com.pd.security.base.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * @author peramdy on 2018/6/2.
 */
public abstract class BaseModel<T> implements Serializable {

    /**
     * 主键
     */
    protected Long id;

    /**
     * 额外sql属性
     */
    protected Map<String, String> sqlMap;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getSqlMap() {
        return sqlMap;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
