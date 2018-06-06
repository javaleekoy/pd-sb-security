package com.pd.security.mapper;

import com.pd.security.base.dao.CrudDao;
import com.pd.security.model.Menu;
import com.pd.security.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author peramdy on 2018/6/1.
 */
public interface MenuMapper extends CrudDao<Menu> {

    List<Menu> queryAll();

    List<Menu> queryMenuByUserId(@Param("userId") Long userId);

}
