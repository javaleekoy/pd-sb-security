package com.pd.security.constants;

/**
 * @author peramdy on 2018/5/24.
 */

public abstract class ShiroConstants {

    public static final String SALT = "hello";

    public static final Integer PASSWORD_RETRY_TIME = 3;

    public static final String PASSWORD_RETRY_EHCACHE_NAME = "pdRetryLimitPassword";

    public static final String EHCACHE_XML_PATH = "ehcache/pd-ehcache.xml";


}
