package com.sudang.myspringsecurity.config;

import com.sudang.myspringsecurity.security.AuthenticationLoggingFilter;
import com.sudang.myspringsecurity.security.RequestValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class ProjectConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http
                // BasicAuthenticationFilter 앞에 RequestValidationFilter 추가
                .addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
                // BasicAuthenticationFilter 뒤에 AuthenticationLoggingFilter 추가
                .addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
