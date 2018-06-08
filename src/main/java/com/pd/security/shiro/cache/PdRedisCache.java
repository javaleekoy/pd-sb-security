package com.pd.security.shiro.cache;

import com.pd.security.cache.redis.PdRedisClient;
import com.pd.security.utils.ToolsUtil;
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
        Jedis jedis = null;
        try {
        /*HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (httpServletRequest != null) {
            v = (V) httpServletRequest.getAttribute(cacheKeyName);
            if (v != null) {
                return v;
            }
        }*/
            jedis = PdRedisClient.create();
            System.out.println(ToolsUtil.getBytes(key));
            System.out.println(ToolsUtil.getBytes(cacheKeyName));
            byte[] bytes = jedis.hget(ToolsUtil.getBytes(cacheKeyName), ToolsUtil.getBytes(key));
            v = (V) ToolsUtil.deserialize(bytes);
        /*if (httpServletRequest != null && v != null) {
            httpServletRequest.setAttribute(cacheKeyName, v);
        }*/
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return v;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        if (key == null) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = PdRedisClient.create();
            jedis.hset(ToolsUtil.getBytes(cacheKeyName), ToolsUtil.getBytes(key), ToolsUtil.serialize(value));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        V v;
        if (key == null) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = PdRedisClient.create();
            v = get(key);
            jedis.hdel(ToolsUtil.getBytes(cacheKeyName), ToolsUtil.getBytes(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return v;
    }

    @Override
    public void clear() throws CacheException {
        Jedis jedis = null;
        try {
            jedis = PdRedisClient.create();
            jedis.hdel(ToolsUtil.getBytes(cacheKeyName));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public int size() {
        int size = 0;
        Jedis jedis = null;
        try {
            jedis = PdRedisClient.create();
            size = jedis.hlen(ToolsUtil.getBytes(cacheKeyName)).intValue();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        jedis.close();
        return size;
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = new HashSet<K>();
        Jedis jedis = null;
        try {
            jedis = PdRedisClient.create();
            Set<byte[]> keySet = jedis.hkeys(ToolsUtil.getBytes(cacheKeyName));
            for (byte[] key : keySet) {
                Object object = ToolsUtil.deserialize(key);
                keys.add((K) object);
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = Collections.emptyList();
        Jedis jedis = null;
        try {
            jedis = PdRedisClient.create();
            List<byte[]> list = jedis.hvals(ToolsUtil.getBytes(cacheKeyName));
            for (byte[] val : list) {
                Object obj = ToolsUtil.deserialize(val);
                values.add((V) obj);
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        jedis.close();
        return values;
    }
}
