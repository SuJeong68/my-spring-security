package com.sudang.myspringsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * UserDetailsService, PasswordEncoder 빈에 대한 구성 클래스
 *
 * @author sudang
 * @date 2023/05/13
 **/
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    // UserDetailsService, PasswordEncoder 빈을 정의하는 대신 아래의 메서드로 설정 가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var userDetailsService = new InMemoryUserDetailsManager();

        var user = User.withUsername("sudang")
                .password("12345")
                .authorities("read")
                .build();
        userDetailsService.createUser(user);

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
