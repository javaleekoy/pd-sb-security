package com.pd.security.mapper;

import com.pd.security.base.dao.CrudDao;
import com.pd.security.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author peramdy on 2018/6/1.
 */
public interface UserMapper extends CrudDao<User> {

    /**
     * 查询用户信息
     *
     * @param loginName 登录名
     * @return
     */
    User queryInfoByLoginName(@Param("loginName") String loginName);

}