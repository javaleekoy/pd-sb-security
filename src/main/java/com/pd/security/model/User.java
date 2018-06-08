package com.pd.security.model;

import com.pd.security.base.model.DataModel;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * @author peramdy on 2018/6/2.
 */
public class User extends DataModel<User> {

    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String phone;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 最后一次登陆时间
     */
    private Date loginDate;
    /**
     * 是否过期
     */
    private Integer isExpired;
    /**
     * 是否可用
     */
    private Integer isDisabled;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Integer isExpired) {
        this.isExpired = isExpired;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(Long id) {
        return id != null && id.equals(1);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
