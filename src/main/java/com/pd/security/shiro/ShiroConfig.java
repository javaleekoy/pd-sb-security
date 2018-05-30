package com.pd.security.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author peramdy on 2018/5/18.
 */
@Configuration
public class ShiroConfig {

    @Value("${redis.ip}")
    private String host;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.timeout}")
    private Integer timeout;
    @Value("${redis.expire}")
    private Integer expire;


    /**
     * shiro 过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/shiro/logout", "logout");
        filterChainDefinitionMap.put("/static/*", "anon");
        filterChainDefinitionMap.put("/shiro/login", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setLoginUrl("/shiro/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 加密规则
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        //hash加密(原生态)
        /*HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();*/
        //hash加密继承HashedCredentialsMatcher，限制了密码错误次数
        MyCredentialsMatcher hashedCredentialsMatcher = new MyCredentialsMatcher();
        //加密类型
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }


    /**
     * 自定义校验账号
     *
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        /**设置realm中自定义身份验证**/
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }


    /**
     * redisManager(shiro-redis插件)
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(expire);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        /**设置redisManager,redis相关配置**/
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * session 管理
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        /**设置sessionDao,session存储**/
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

    /**
     * shiro (redis)缓存管理
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        /**设置redisManager,redis相关配置**/
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 初始化securityManager
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        /***设置自定义realm***/
        securityManager.setRealm(myShiroRealm());
        /***设置自定义sessionManager***/
        securityManager.setSessionManager(sessionManager());
        /***设置自定义cacheManager***/
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }


    /**
     * 添加注解授权
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
