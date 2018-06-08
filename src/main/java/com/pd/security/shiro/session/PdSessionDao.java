package com.pd.security.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;

import java.util.Collection;

/**
 * @author peramdy on 2018/6/5.
 */
public interface PdSessionDao extends SessionDAO {


    /**
     * 获取session集合
     *
     * @param includeLeave
     * @return
     */
    public Collection<Session> getActiveSession(boolean includeLeave);


    /**
     * 获取session集合
     *
     * @param includeLeave
     * @param principal
     * @param filterSession
     * @return
     */
    public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession);

}
