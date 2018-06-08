package com.pd.security.constants;

/**
 * @author peramdy on 2018/5/24.
 */

public abstract class ShiroConstants {

    /**
     * 密码加密盐
     */
    public static final String SALT = "hello";

    /**
     * 密码加密规则
     */
    public static final String HASH_ALGORITHM_MD5 = "md5";

    /**
     * 密码加密散列次数
     */
    public static final Integer HASH_ITERATIONS = 2;

    /**
     * 密码重试次数
     */
    public static final Integer PASSWORD_RETRY_TIME = 3;

    /**
     * ehcache纪录密码重试次数
     */
    public static final String PASSWORD_RETRY_EHCACHE_NAME = "pdRetryLimitPassword";

    /**
     * ehcache配置文件路径
     */
    public static final String EHCACHE_XML_PATH = "ehcache/pd-ehcache.xml";

    /**
     * session在redis中缓存名称
     */
    public static final String SESSION_KEY_PREFIX = "shiro_redis_session_";

    /**
     * shiro的cache在redis中缓存名称
     */
    public static final String CACHE_KEY_PREFIX = "shiro_redis_cache_";

    /**
     * session超时时间
     */
    public static final Integer GLOBAL_SESSION_TIMEOUT = 600000;


}
