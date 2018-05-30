package com.pd.security.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author peramdy on 2018/5/18.
 */
public class MySessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "authorization";
    /*private static final String REFERENCED_SESSION_ID_SOURCE = "stateless request";*/

    public MySessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String auth = httpServletRequest.getHeader(AUTHORIZATION);
        if (StringUtils.isNotBlank(auth)) {

            /**设置session状态**/
            /*request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);*/
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, auth);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return auth;
        }
        return super.getSessionId(request, response);
    }
}
