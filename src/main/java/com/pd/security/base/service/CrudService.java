package com.pd.security.base.service;

import com.pd.security.base.dao.CrudDao;
import com.pd.security.base.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author peramdy on 2018/6/2.
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public abstract class CrudService<D extends CrudDao<T>, T extends DataModel<T>> extends BaseService {

    @Autowired
    protected D dao;

    /**
     * get 方法(主键)
     *
     * @param id 主键
     * @return
     */
    public T get(Long id) {
        return dao.get(id);
    }

    /**
     * get 方法(查询条件)
     *
     * @param model
     * @return
     */
    public T get(T model) {
        return dao.get(model);
    }

    /**
     * 查询
     *
     * @param model
     * @return
     */
    public List<T> queryList(T model) {
        return dao.queryList(model);
    }

    /**
     * 添加
     *
     * @param model
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(T model) {
        dao.insert(model);
    }

    /**
     * 更新
     *
     * @param model
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(T model) {
        dao.update(model);
    }

    /**
     * 删除
     *
     * @param model
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void del(T model) {
        dao.del(model);
    }
}
