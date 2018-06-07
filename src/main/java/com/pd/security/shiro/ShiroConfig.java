package com.pd.security.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.pd.security.shiro.cache.PdRedisCacheManager;
import com.pd.security.shiro.session.PdRedisSessionDao;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.pd.security.constants.ShiroConstants.*;

/**
 * @author peramdy on 2018/5/18.
 */
@Configuration
public class ShiroConfig {

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
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/static/*", "anon");
        /*不是使用注解标签时可以这样定义标签权限*/
        /*filterChainDefinitionMap.put("/user/hello", "perms[pd:hello:view]");*/
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unAuth");
        return shiroFilterFactoryBean;
    }


    /**
     * 凭证匹配器（加密规则、散列次数、密码错误次数限制等等）
     *
     * @return
     */
    @Bean
    public PdCredentialsMatcher pdCredentialsMatcher() {
        //hash加密继承HashedCredentialsMatcher，限制了密码错误次数
        PdCredentialsMatcher pdCredentialsMatcher = new PdCredentialsMatcher();
        //加密类型
        pdCredentialsMatcher.setHashAlgorithmName(HASH_ALGORITHM_NAME);
        //散列次数
        pdCredentialsMatcher.setHashIterations(HASH_ITERATIONS);
        return pdCredentialsMatcher;
    }


    /**
     * 自定义校验账号
     *
     * @return
     */
    @Bean
    public PdShiroRealm myShiroRealm() {
        PdShiroRealm myShiroRealm = new PdShiroRealm();
        /**设置realm中自定义身份验证**/
        myShiroRealm.setCredentialsMatcher(pdCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 缓存管理
     *
     * @return
     */
    public PdRedisCacheManager pdRedisCacheManager() {
        PdRedisCacheManager pdRedisCacheManager = new PdRedisCacheManager();
        return pdRedisCacheManager;
    }

    /**
     * 设置redisManager,redis相关配置
     *
     * @return
     **/
    @Bean
    public PdRedisSessionDao pdRedisSessionDao() {
        PdRedisSessionDao pdRedisSessionDao = new PdRedisSessionDao();
        return pdRedisSessionDao;
    }

    /**
     * session 管理
     *
     * @return
     */
    @Bean
    public PdSessionManager pdSessionManager() {
        PdSessionManager pdSessionManager = new PdSessionManager();
        /**设置sessionDao,session存储**/
        pdSessionManager.setSessionDAO(pdRedisSessionDao());
        /**设置session 过期时间**/
        pdSessionManager.setGlobalSessionTimeout(GLOBAL_SESSION_TIMEOUT);
        return pdSessionManager;
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
        securityManager.setSessionManager(pdSessionManager());
        /***设置自定义cacheManager***/
        securityManager.setCacheManager(pdRedisCacheManager());
        return securityManager;
    }

    /**
     * 开启shiro的注解支持
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 使用shiro框架提供的切面类，用于创建代理对象
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


    /**
     * thymeleaf-shiro:thymeleaf支持shiro标签解析
     *
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
