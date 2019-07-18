package com.encdata.corn.niblet.security;

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    public KeycloakClientRequestFactory keycloakClientRequestFactory;

    @Autowired
    private UrlLogoutHandler urlLogoutHandler;

    // ClientToClient使用到的
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate(){
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        return sessionRegistry;
    }

    // Registers the KeycloakAuthenticationProvider with the authentication manager.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
//        grantedAuthorityMapper.setPrefix("ROLE_");
//        grantedAuthorityMapper.setConvertToUpperCase(true);
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }


    // Defines the session authentication strategy.
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

//    @Bean
//    public KeycloakConfigResolver keycloakConfigResolver(){
//        return new KeycloakConfigResolverNew();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        http.headers().frameOptions().disable();
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).anonymous()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().maximumSessions(-1).sessionRegistry(sessionRegistry)
                .and()
                .and()
                .logout().addLogoutHandler(urlLogoutHandler)
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .httpBasic();
        
    }
}
