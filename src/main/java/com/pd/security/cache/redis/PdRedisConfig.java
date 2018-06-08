package com.pd.security.cache.redis;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author peramdy on 2018/6/6.
 *         获取配置文件中的属性值
 */
@Component
@ConfigurationProperties(prefix = "pd.redis")
public class PdRedisConfig {

    private String ip;
    private Integer port;
    private Integer timeout;
    private Integer expire;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
