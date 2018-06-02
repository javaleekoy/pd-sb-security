package com.pd.security.shiro;

import com.pd.security.service.UserService;
import com.pd.security.web.dto.UserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

import static com.pd.security.constants.ShiroConstants.SALT;

/**
 * @author peramdy on 2018/5/18.
 */
public class PdShiroRealm extends AuthorizingRealm {

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

        getAvailablePrincipal(principalCollection);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Principal userInfo = (Principal) principalCollection.getPrimaryPrincipal();
        /*for (SysRole role : userInfo.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getRole());
            for (String permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission);
            }
        }*/
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
        PdUsernamePasswordToken token = (PdUsernamePasswordToken) authenticationToken;
        String username = (String) token.getPrincipal();
        UserDto userDto = userService.queryUserInfo(username);
        if (userDto == null) {
            return null;
        }
        if (userDto.getDisabled() == 1 || userDto.getDel() == 1) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new Principal(userDto, token.isMobileLogin()),
                userDto.getPassword(),
                ByteSource.Util.bytes(SALT),
                getName()
        );
        return authenticationInfo;
    }


    public static class Principal implements Serializable {


        private Long id;
        private String loginName;
        private String name;
        private boolean isMobileLogin;

        public Principal(UserDto user, boolean isMobileLogin) {
            this.id = user.getId();
            this.loginName = user.getLoginName();
            this.name = user.getName();
            this.isMobileLogin = isMobileLogin;
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isMobileLogin() {
            return isMobileLogin;
        }

        public void setMobileLogin(boolean mobileLogin) {
            isMobileLogin = mobileLogin;
        }

        public String getSessionId() {
            try {
                return (String) SecurityUtils.getSubject().getSession().getId();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

}
