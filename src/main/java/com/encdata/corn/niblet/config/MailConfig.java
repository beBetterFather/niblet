package com.encdata.corn.niblet.config;

import com.encdata.core.mail.MailFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 郵件配置
 * @Date 16:59 2019/8/21
 * @Author SiweiJin
 **/
@Configuration
public class MailConfig {

    @Value(value = "${spring.mail.host}")
    private String mailHost;

    @Value(value = "${spring.mail.port}")
    private int port;

    @Value(value = "${spring.mail.username}")
    private String userName;

    @Value(value = "${spring.mail.password}")
    private String password;

    @Bean
    public MailFactory mailFactory(){
        MailFactory mailFactory = new MailFactory();
        mailFactory.setMailHost(mailHost);
        mailFactory.setPort(port);
        mailFactory.setUsername(userName);
        mailFactory.setPassword(password);
        return mailFactory;
    }
}
