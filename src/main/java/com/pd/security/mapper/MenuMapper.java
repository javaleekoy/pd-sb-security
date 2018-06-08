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

    /**
     * 查询所有
     *
     * @return
     */
    List<Menu> queryAll();

    /**
     * 根据用户Id查询
     *
     * @param userId 用户Id
     * @return
     */
    List<Menu> queryMenuByUserId(@Param("userId") Long userId);

}
