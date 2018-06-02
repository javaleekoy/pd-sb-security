package com.pd.security.shiro;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;

import java.util.concurrent.atomic.AtomicInteger;

import static com.pd.security.constants.ShiroConstants.*;

/**
 * @author peramdy on 2018/5/25
 *         <p>
 *         ehcache 缓存密码登陆错误次数
 */
public class PdCredentialsMatcher extends HashedCredentialsMatcher {


    private Cache cache;

    public PdCredentialsMatcher() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:" + EHCACHE_XML_PATH);
        cache = ehCacheManager.getCache(PASSWORD_RETRY_EHCACHE_NAME);
    }

    /**
     * 验证登陆登录密码次数
     *
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        /****登录密码错误次数严重****/
        String userName = (String) token.getPrincipal();
        Object object = cache.get(userName);
        if (object == null) {
            cache.put(userName, new AtomicInteger(0));
        }
        AtomicInteger atomicInteger = (AtomicInteger) cache.get(userName);
        if (atomicInteger.incrementAndGet() > PASSWORD_RETRY_TIME) {
            throw new ExcessiveAttemptsException();
        }
        /***登陆密码严重***/
        boolean matcher = super.doCredentialsMatch(token, info);
        if (matcher) {
            cache.remove(userName);
        }
        return matcher;
    }
}
