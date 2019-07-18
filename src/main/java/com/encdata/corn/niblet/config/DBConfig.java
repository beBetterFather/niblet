package com.encdata.corn.niblet.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Author: jinsi
 * @Date: 2019/7/16 18:15
 * @Description: 数据源配置
 */
@Configuration
public class DBConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.ds-user")
    public DataSourceProperties userDataSourceProperties(){
        return new DataSourceProperties();
    }

    //通过 HikariCP 创建用户数据源
    @Bean(name = "userDataSource")
    @Primary
    public DataSource userDataSource(){
        return userDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource") DataSource userDataSource){
        return new JdbcTemplate(userDataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.ds-order")
    public DataSourceProperties orderDataSourceProperties(){
        return new DataSourceProperties();
    }

    //通过 HikariCP 创建订单数据源
    @Bean(name = "orderDataSource")
    public DataSource orderDataSource(){
        return orderDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource orderDataSource){
        return new JdbcTemplate(orderDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        //用户数据库事务
        DataSourceTransactionManager useTM = new DataSourceTransactionManager(userDataSource());
        //订单数据事务
        DataSourceTransactionManager orderTM = new DataSourceTransactionManager(orderDataSource());
        ChainedTransactionManager chainedTransactionManager = new ChainedTransactionManager(useTM, orderTM);
        return chainedTransactionManager;
    }


}
