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

    public T get(Long id) {
        return dao.get(id);
    }

    public T get(T model) {
        return dao.get(model);
    }

    public List<T> queryList(T model) {
        return dao.queryList(model);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(T model) {
        dao.insert(model);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(T model) {
        dao.update(model);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void del(T model) {
        dao.del(model);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void del(Long id) {
        dao.del(id);
    }

}
