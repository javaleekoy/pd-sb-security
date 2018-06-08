package com.pd.security.cache.redis;

import com.pd.security.spring.PdSpringContextHolder;
import com.pd.spring.redis.builder.JedisBuilder;
import redis.clients.jedis.Jedis;

/**
 * @author peramdy on 2018/6/6.
 */
public class PdRedisClient {
    /**
     * 获取redis配置类
     */
    private static PdRedisConfig pdRedisConfig = PdSpringContextHolder.getBean(PdRedisConfig.class);

    /**
     * 创建jedis
     *
     * @return
     */
    public static Jedis create() {
        JedisBuilder jedisBuilder = JedisBuilder.getInstance();
        jedisBuilder.setHost(pdRedisConfig.getIp());
        jedisBuilder.setPort(pdRedisConfig.getPort());
        jedisBuilder.setTimeout(pdRedisConfig.getTimeout());
        try {
            return jedisBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
