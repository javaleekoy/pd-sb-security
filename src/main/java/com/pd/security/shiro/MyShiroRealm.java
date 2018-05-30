package com.pd.security.shiro;

import com.pd.security.model.SysRole;
import com.pd.security.model.UserInfo;
import com.pd.security.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import static com.pd.security.constants.ShiroConstants.SALT;

/**
 * @author peramdy on 2018/5/18.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    @Override
    public String getName() {
        return "myShiroRealm";
    }

    /**
     * 权限验证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
        for (SysRole role : userInfo.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getRole());
            for (String permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission);
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 登录身份验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        UserInfo userInfo = userService.queryUserInfo(username);
        if (userInfo == null) {
            return null;
        }
        if (userInfo.getStatus() == 2 || userInfo.getDel() == 1) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(SALT),
                getName()
        );
        return authenticationInfo;
    }

}
