package com.sudang.myspringsecurity.config;

import com.sudang.myspringsecurity.security.StaticKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class ProjectConfig {

    @Autowired
    private StaticKeyAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.addFilterAt(filter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
