package com.encdata.corn.niblet.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @ClassName MybatisConfig
 * @Description mybatis配置类
 * @Author SiweiJin
 * @Date 2019/8/22 16:14
 * @Version 1.0
 **/
@Configuration
public class MybatisConfig {

//    @Bean(name = "blogDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.ds-blog")
//    public DataSource blogDataSource() {
//        DruidDataSource datasource = new DruidDataSource();
//        return datasource;
//    }

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(
            ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);//use下划线与驼峰式命名规则的映射（如first_name => firstName）
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setLogImpl(org.apache.ibatis.logging.log4j.Log4jImpl.class);//use log4j log
        sessionFactory.setConfiguration(configuration);
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"));

        return sessionFactory;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("**.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }
}
