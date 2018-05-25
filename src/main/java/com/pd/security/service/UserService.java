package com.pd.security.service;

import com.pd.security.model.SysRole;
import com.pd.security.model.UserInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author peramdy on 2018/5/18.
 */
@Service
public class UserService {

    public UserInfo queryUserInfo(String userName) {
        UserInfo userInfo = new UserInfo();
        SysRole sysRole = new SysRole();
        sysRole.setId(1);
        sysRole.setRole("root");
        List<String> list = new ArrayList<String>();
        list.add("all");
        sysRole.setPermissions(list);
        userInfo.setId(1);
        userInfo.setDel(0);
        userInfo.setName("huahua");
        userInfo.setStatus(1);
        //password:111111
        /*userInfo.setPassword("7b76b0fbaa69fa2fc77359dc3e30302c");*/
        //password:123456
        userInfo.setPassword("ef833cc8e3455be68e0df56555dddc4d");
        List<SysRole> sysRoleList = new ArrayList<SysRole>();
        sysRoleList.add(sysRole);
        userInfo.setRoles(sysRoleList);
        return userInfo;
    }

}
