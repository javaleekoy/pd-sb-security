package com.pd.security.mapper;

import com.pd.security.base.dao.CrudDao;
import com.pd.security.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author peramdy on 2018/6/1.
 */
public interface RoleMapper extends CrudDao<Role> {

    /**
     * 根据用户Id查询
     *
     * @param userId
     * @return
     */
    List<Role> queryRolesByUserId(@Param("userId") Long userId);

}