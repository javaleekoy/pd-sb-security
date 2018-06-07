package com.pd.security.shiro;

import com.pd.security.model.Menu;
import com.pd.security.model.Role;
import com.pd.security.service.MenuService;
import com.pd.security.service.RoleService;
import com.pd.security.service.UserService;
import com.pd.security.shiro.session.PdRedisSessionDao;
import com.pd.security.web.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.pd.security.constants.ShiroConstants.SALT;

/**
 * @author peramdy on 2018/5/18.
 */
public class PdShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PdRedisSessionDao pdRedisSessionDao;


    @Override
    public String getName() {
        return "pdShiroRealm";
    }

    /**
     * 权限验证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        Collection<Session> sessions = pdRedisSessionDao.getActiveSessions(true, principal, SecurityUtils.getSubject().getSession(false));
        if (sessions.size() > 0) {
            if (SecurityUtils.getSubject().isAuthenticated()) {
                for (Session session : sessions) {
                    pdRedisSessionDao.delete(session);
                }
            } else {
                SecurityUtils.getSubject().logout();
            }
        }

        UserDto userDto = userService.queryUserInfo(principal.getName());
        if (userDto == null) {
            return null;
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        /**角色查询**/
        List<Role> roles = roleService.queryRolesByUserId(userDto.getId());
        boolean isAdmin = false;
        for (Role role : roles) {
            simpleAuthorizationInfo.addRole(role.getName());
            if (role.getRoleType().equals(1)) {
                isAdmin = true;
            }
        }

        /**权限菜单查询**/
        List<Menu> menus;
        if (isAdmin) {
            menus = menuService.queryAll();
        } else {
            menus = menuService.queryMenuByUserId(userDto.getId());
        }
        for (Menu menu : menus) {
            if (StringUtils.isNotBlank(menu.getPermission())) {
                simpleAuthorizationInfo.addStringPermission(menu.getPermission());
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
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = (String) token.getPrincipal();
        UserDto userDto = userService.queryUserInfo(username);
        if (userDto == null) {
            return null;
        }
        if (userDto.getIsDisabled() == 1 || userDto.getDel() == 1) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new Principal(userDto),
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


        public Principal(UserDto user) {
            this.id = user.getId();
            this.loginName = user.getLoginName();
            this.name = user.getName();
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
