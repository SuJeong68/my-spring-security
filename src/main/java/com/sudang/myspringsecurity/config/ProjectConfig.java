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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 인-메모리 사용자를 위한 구성
        // 가능하면 애플리케이션의 책임을 분리해서 작성하는 것이 좋기 때문에 일반적으로 이 접근 방식 권장하지 않음.
        auth.inMemoryAuthentication()
                .withUser("sudang")
                .password("12345")
                .authorities("read")
            .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
