package com.pd.security.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

import static com.pd.security.constants.DataSourceConstants.*;

/**
 * @author peramdy on 2018/6/1.
 */
@Configuration
@MapperScan("com.pd.security.mapper")
public class DataSourceConfig implements EnvironmentAware {

    private RelaxedPropertyResolver relaxedPropertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.relaxedPropertyResolver = new RelaxedPropertyResolver(environment, DB_PREFIX);
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(relaxedPropertyResolver.getProperty(DB_URL));
        dataSource.setDriverClassName(relaxedPropertyResolver.getProperty(DB_DRIVER));
        dataSource.setUsername(relaxedPropertyResolver.getProperty(DB_USER));
        dataSource.setPassword(relaxedPropertyResolver.getProperty(DB_PASSWORD));
        dataSource.setInitialSize(Integer.parseInt(relaxedPropertyResolver.getProperty(DB_INITIAL_SIZE)));
        dataSource.setMinIdle(Integer.parseInt(relaxedPropertyResolver.getProperty(DB_MIN_IDLE)));
        dataSource.setMaxActive(Integer.parseInt(relaxedPropertyResolver.getProperty(DB_MAX_ACTIVE)));
        dataSource.setMaxWait(Integer.parseInt(relaxedPropertyResolver.getProperty(DB_MAX_WAIT)));
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(patternResolver.getResources("classpath:mapper/*.xml"));
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }

}
