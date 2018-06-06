package com.pd.security.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;


import static com.pd.security.constants.ShiroConstants.*;

/**
 * @author peramdy on 2018/6/5.
 */
public class PdRedisCacheManager implements CacheManager {


    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new PdRedisCache<K, V>(CACHE_KEY_PREFIX + name);
    }
}
