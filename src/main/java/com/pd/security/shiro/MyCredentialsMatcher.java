package com.pd.security.shiro;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pd.security.constants.ShiroConstants.*;

/**
 * @author peramdy on 2018/5/25.
 */
public class MyCredentialsMatcher extends HashedCredentialsMatcher {

    private Ehcache ehcache;

    public MyCredentialsMatcher() {
        URL url = CacheManager.class.getClassLoader().getResource(EHCACHE_XML_PATH);
        CacheManager cacheManager = CacheManager.newInstance(url);
        ehcache = cacheManager.getCache(PASSWORD_RETRY_EHCACHE_NAME);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String) token.getPrincipal();
        Element element = ehcache.get(userName);
        if (element == null) {
            element = new Element(userName, new AtomicInteger(0));
            ehcache.put(element);
        }
        AtomicInteger atomicInteger = (AtomicInteger) element.getObjectValue();
        if (atomicInteger.incrementAndGet() > PASSWORD_RETRY_TIME) {
            throw new ExcessiveAttemptsException();
        }
        boolean matcher = super.doCredentialsMatch(token, info);
        if (matcher) {
            ehcache.remove(userName);
        }
        return matcher;
    }
}
