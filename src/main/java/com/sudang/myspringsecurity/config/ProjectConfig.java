package com.sudang.myspringsecurity.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * @author sudang
 * @date   2023/05/14
**/
// 실제 어플리케이션에선 책임 분리를 선호하며 주 클래스를 구성 클래스로 이용하지 않기 때문에 Configuration 위에 지정
@EnableAsync
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String usersByUsernameQuery = "SELECT username, password, enabled FROM users WHERE username = ?";
        String authoritiesByUsernameQuery = "SELECT username, authority FROM spring.authorities WHERE username = ?";

        var userDetailManager = new JdbcUserDetailsManager(dataSource);
        userDetailManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailManager.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);

        return userDetailManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public InitializingBean initializingBean() {
        // 원래 스레드에 있는 세부 정보를 비동기 메서드의 새로 생성된 스레드로 복사
        // 이 방식은 프레임워크가 자체적으로 스레드를 만들때만 작동 (@Async) = 코드로 스레드 생성 시 활성화해도 작동 안함
        // return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        // 모든 스레드에 보안 컨텍스트 공유
        return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
    }
}
