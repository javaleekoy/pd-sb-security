package com.pd.security.service;

import com.pd.security.base.service.CrudService;
import com.pd.security.mapper.RoleMapper;
import com.pd.security.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author peramdy on 2018/6/6.
 */
@Service
public class RoleService extends CrudService<RoleMapper, Role> {

    /**
     * 根据用户Id查询
     *
     * @param userId 用户Id
     * @return
     */
    public List<Role> queryRolesByUserId(Long userId) {
        return dao.queryRolesByUserId(userId);
    }

}
