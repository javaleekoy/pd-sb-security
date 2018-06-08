package com.pd.security.constants;

/**
 * @author peramdy on 2018/6/1.
 *         database 配置常量
 */
public abstract class DataSourceConstants {

    /**
     * 属性文件前缀
     */
    public static final String DB_PREFIX = "pd.db.";
    /**
     * 数据库地址
     */
    public static final String DB_URL = "url";
    /**
     * 用户名
     */
    public static final String DB_USER = "username";
    /**
     * 密码
     */
    public static final String DB_PASSWORD = "password";
    /**
     * 驱动
     */
    public static final String DB_DRIVER = "driverClass";
    /**
     * 初始化大小
     */
    public static final String DB_INITIAL_SIZE = "initialSize";
    /**
     * 最大活跃数
     */
    public static final String DB_MAX_ACTIVE = "maxActive";
    /**
     * 最小活跃数
     */
    public static final String DB_MIN_IDLE = "minIdle";
    /**
     * 最大等待时长
     */
    public static final String DB_MAX_WAIT = "maxWait";

}
