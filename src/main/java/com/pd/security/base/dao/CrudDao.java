package com.pd.security.base.dao;

import java.util.List;

/**
 * @author peramdy on 2018/6/2.
 */
public interface CrudDao<T> extends BaseDao {

    /**
     * get 方法(主键查询)
     *
     * @param id 主键
     * @return
     */
    T get(Long id);

    /**
     * get 方法(参数查询)
     *
     * @param model 条件
     * @return
     */
    T get(T model);

    /**
     * 条件查询
     *
     * @param model 条件
     * @return
     */
    List<T> queryList(T model);

    /**
     * 添加
     *
     * @param model
     * @return
     */
    Integer insert(T model);

    /**
     * 更新
     *
     * @param model
     * @return
     */
    Integer update(T model);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Integer del(Long id);

    /**
     * 删除
     *
     * @param model
     * @return
     */
    Integer del(T model);

}
