package com.pd.security.shiro.session;

import com.pd.security.cache.redis.PdRedisClient;
import com.pd.security.utils.ToolsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

import static com.pd.security.constants.ShiroConstants.SESSION_KEY_PREFIX;

/**
 * @author peramdy on 2018/6/5.
 */
@Component
public class PdRedisSessionDao extends AbstractSessionDAO implements PdSessionDao {

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }
        /*HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();*/
        PrincipalCollection pc = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        String principalId = pc != null ? pc.getPrimaryPrincipal().toString() : StringUtils.EMPTY;
        Jedis jedis = PdRedisClient.create();
        jedis.hset(SESSION_KEY_PREFIX, session.getId().toString(), principalId + "|" + session.getTimeout() + "|" + session.getLastAccessTime().getTime());
        byte[] key = ToolsUtil.getBytes(SESSION_KEY_PREFIX + session.getId());
        byte[] value = ToolsUtil.serialize(session);
        jedis.set(key, value);
        int timeout = (int) (session.getTimeout() / 1000);
        jedis.expire(ToolsUtil.getBytes(SESSION_KEY_PREFIX + session.getId()), timeout);
        jedis.close();
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        Jedis jedis = PdRedisClient.create();
        jedis.hdel(SESSION_KEY_PREFIX, session.getId().toString());
        jedis.del(ToolsUtil.getBytes(SESSION_KEY_PREFIX + session.getId()));
        jedis.close();
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return getActiveSession(true);
    }

    @Override
    public Collection<Session> getActiveSession(boolean includeLeave) {
        return getActiveSessions(includeLeave, null, null);
    }

    @Override
    public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
        Set<Session> sessions = new HashSet<Session>();
        Jedis jedis = PdRedisClient.create();
        Map<String, String> map = jedis.hgetAll(SESSION_KEY_PREFIX);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
                String[] strArray = StringUtils.split(entry.getValue(), "|");
                if (strArray != null && strArray.length == 3) {
                    SimpleSession simpleSession = new SimpleSession();
                    simpleSession.setId(entry.getKey());
                    simpleSession.setAttribute("principalId", strArray[0]);
                    simpleSession.setTimeout(Long.valueOf(strArray[1]));
                    simpleSession.setLastAccessTime(new Date(Long.valueOf(strArray[2])));
                    try {
                        simpleSession.validate();
                        long pastMins = ((System.currentTimeMillis() - Long.valueOf(Long.valueOf(strArray[2]))) / (60 * 30));
                        boolean isActiveSession = false;
                        if (includeLeave || pastMins < 3) {
                            isActiveSession = true;
                        }
                        if (principal != null) {
                            PrincipalCollection pc = (PrincipalCollection) simpleSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                            if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : StringUtils.EMPTY)) {
                                isActiveSession = true;
                            }
                        }
                        if (filterSession != null && filterSession.getId().equals(simpleSession.getId())) {
                            isActiveSession = false;
                        }
                        if (isActiveSession) {
                            sessions.add(simpleSession);
                        }
                        /**session 过期删掉**/
                    } catch (Exception e2) {
                        jedis.hdel(SESSION_KEY_PREFIX, entry.getKey());
                    }
                } else {
                    jedis.hdel(SESSION_KEY_PREFIX, entry.getKey());
                }
            } else if (StringUtils.isNotBlank(entry.getKey())) {
                jedis.hdel(SESSION_KEY_PREFIX, entry.getKey());
            }
        }
        jedis.close();
        return sessions;
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.update(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        /*HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Session session = (Session) httpServletRequest.getAttribute("pdSession" + sessionId);
        if (session != null) {
            return session;
        }*/
        Jedis jedis = PdRedisClient.create();
        byte[] sessionBytes = jedis.get(ToolsUtil.getBytes(SESSION_KEY_PREFIX + sessionId));
        session = (Session) ToolsUtil.deserialize(sessionBytes);
        jedis.close();
        return session;
    }


    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        return super.readSession(sessionId);
    }


}
