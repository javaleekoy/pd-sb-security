package com.pd.security.service;

import com.pd.security.base.service.CrudService;
import com.pd.security.mapper.MenuMapper;
import com.pd.security.model.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author peramdy on 2018/6/6.
 */
@Service
public class MenuService extends CrudService<MenuMapper, Menu> {

    public List<Menu> queryAll() {
        return dao.queryAll();
    }

    public List<Menu> queryMenuByUserId(Long userId) {
        return dao.queryMenuByUserId(userId);
    }

}
