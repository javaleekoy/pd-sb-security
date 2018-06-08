package com.pd.security.spring;

import com.pd.security.exception.PdException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author peramdy on 2018/6/6.
 */
@Component
@Lazy(value = false)
public class PdSpringContextHolder implements ApplicationContextAware, DisposableBean {


    private static ApplicationContext applicationContext;


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> t) {
        return applicationContext.getBean(t);
    }

    public static <T> T getBean(String className) {
        return (T) applicationContext.getBean(className);
    }

    public static void clearHolder() {
        applicationContext = null;
    }


    /**
     * @throws Exception
     */
    @Override
    public void destroy() throws PdException {
        PdSpringContextHolder.clearHolder();
    }

    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PdSpringContextHolder.applicationContext = applicationContext;
        /*for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }*/
    }
}
