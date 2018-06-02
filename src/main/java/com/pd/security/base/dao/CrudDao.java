package com.pd.security.base.dao;

import java.util.List;

/**
 * @author peramdy on 2018/6/2.
 */
public interface CrudDao<T> extends BaseDao {


    T get(Long id);

    T get(T model);

    List<T> queryList(T model);

    Integer insert(T model);

    Integer update(T model);

    Integer del(Long id);

    Integer del(T model);

}
