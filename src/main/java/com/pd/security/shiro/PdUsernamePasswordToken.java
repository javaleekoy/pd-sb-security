package com.pd.security.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author peramdy on 2018/6/2.
 */
public class PdUsernamePasswordToken extends UsernamePasswordToken {

    private boolean mobileLogin;

    public PdUsernamePasswordToken() {
        super();
    }


    public PdUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, boolean mobileLogin) {
        super(username, password, rememberMe, host);
        this.mobileLogin = mobileLogin;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }
}
