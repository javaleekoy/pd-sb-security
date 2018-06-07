package com.pd.security.constants;

/**
 * @author peramdy on 2018/5/24.
 */

public abstract class ShiroConstants {

    public static final String SALT = "hello";

    public static final Integer PASSWORD_RETRY_TIME = 3;

    public static final String PASSWORD_RETRY_EHCACHE_NAME = "pdRetryLimitPassword";

    public static final String EHCACHE_XML_PATH = "ehcache/pd-ehcache.xml";

    public static final String SESSION_KEY_PREFIX = "shiro_redis_session_";

    public static final String CACHE_KEY_PREFIX = "shiro_redis_cache_";

    public static final Integer GLOBAL_SESSION_TIMEOUT = 600000;

    public static final String HASH_ALGORITHM_NAME = "md5";

    public static final Integer HASH_ITERATIONS = 2;


}
