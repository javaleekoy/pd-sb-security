package com.pd.security.mapper;

import com.pd.security.base.dao.CrudDao;
import com.pd.security.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author peramdy on 2018/6/1.
 */
public interface RoleMapper extends CrudDao<Role> {

    public List<Role> queryRolesByUserId(@Param("userId") Long userId);

}