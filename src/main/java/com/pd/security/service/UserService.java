package com.pd.security.service;

import com.pd.security.base.service.CrudService;
import com.pd.security.mapper.UserMapper;
import com.pd.security.model.User;
import com.pd.security.web.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author peramdy on 2018/5/18.
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService extends CrudService<UserMapper, User> {

    public UserDto queryUserInfo(String userName) {
        User user = dao.queryInfoByLoginName(userName);
        UserDto dto = null;
        if (user != null) {
            dto = UserDto.newInstance();
            BeanUtils.copyProperties(user, dto);
        }
        return dto;
    }

}
