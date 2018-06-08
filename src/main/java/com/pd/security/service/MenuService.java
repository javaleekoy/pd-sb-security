package com.pd.security.service;

import com.pd.security.base.service.CrudService;
import com.pd.security.exception.PdException;
import com.pd.security.mapper.MenuMapper;
import com.pd.security.model.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author peramdy on 2018/6/6.
 */
@Service
public class MenuService extends CrudService<MenuMapper, Menu> {

    /**
     * 查询所以
     *
     * @return
     */
    public List<Menu> queryAll() throws PdException {
        return dao.queryAll();
    }

    /**
     * 根据用户Id查询
     *
     * @param userId 用户Id
     * @return
     */
    public List<Menu> queryMenuByUserId(Long userId) throws PdException {
        return dao.queryMenuByUserId(userId);
    }

}
