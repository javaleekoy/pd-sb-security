package com.pd.security.shiro.cache;

import com.pd.security.cache.redis.PdRedisClient;
import com.pd.security.utils.ToolsUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author peramdy on 2018/6/5.
 */
public class PdRedisCache<K, V> implements Cache<K, V> {

    private String cacheKeyName;

    public PdRedisCache(String cacheKeyName) {
        this.cacheKeyName = cacheKeyName;
    }

    @Override
    public V get(K key) throws CacheException {
        V v;
        /*HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (httpServletRequest != null) {
            v = (V) httpServletRequest.getAttribute(cacheKeyName);
            if (v != null) {
                return v;
            }
        }*/
        Jedis jedis = PdRedisClient.create();
        System.out.println(ToolsUtil.getBytes(key));
        System.out.println(ToolsUtil.getBytes(cacheKeyName));
        byte[] bytes = jedis.hget(ToolsUtil.getBytes(cacheKeyName), ToolsUtil.getBytes(key));
        v = (V) ToolsUtil.deserialize(bytes);
        /*if (httpServletRequest != null && v != null) {
            httpServletRequest.setAttribute(cacheKeyName, v);
        }*/
        jedis.close();
        return v;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        if (key == null) {
            return null;
        }
        Jedis jedis = PdRedisClient.create();
        jedis.hset(ToolsUtil.getBytes(cacheKeyName), ToolsUtil.getBytes(key), ToolsUtil.serialize(value));
        jedis.close();
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        V v;
        if (key == null) {
            return null;
        }
        Jedis jedis = PdRedisClient.create();
        v = get(key);
        jedis.hdel(ToolsUtil.getBytes(cacheKeyName), ToolsUtil.getBytes(key));
        jedis.close();
        return v;
    }

    @Override
    public void clear() throws CacheException {
        Jedis jedis = PdRedisClient.create();
        jedis.hdel(ToolsUtil.getBytes(cacheKeyName));
        jedis.close();
    }

    @Override
    public int size() {
        int size = 0;
        Jedis jedis = PdRedisClient.create();
        size = jedis.hlen(ToolsUtil.getBytes(cacheKeyName)).intValue();
        jedis.close();
        return size;
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = new HashSet<K>();
        Jedis jedis = PdRedisClient.create();
        Set<byte[]> keySet = jedis.hkeys(ToolsUtil.getBytes(cacheKeyName));
        for (byte[] key : keySet) {
            Object object = ToolsUtil.deserialize(key);
            keys.add((K) object);
        }
        jedis.close();
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = Collections.emptyList();
        Jedis jedis = PdRedisClient.create();
        List<byte[]> list = jedis.hvals(ToolsUtil.getBytes(cacheKeyName));
        for (byte[] val : list) {
            Object obj = ToolsUtil.deserialize(val);
            values.add((V) obj);
        }
        jedis.close();
        return values;
    }
}
