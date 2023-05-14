package com.sudang.myspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * @author sudang
 * @date   2023/05/14
**/
@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String usersByUsernameQuery = "SELECT username, password, enabled FROM users WHERE username = ?";
        String authoritiesByUsernameQuery = "SELECT username, authority FROM spring.authorities WHERE username = ?";

        var userDetailManager = new JdbcUserDetailsManager(dataSource);
        userDetailManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailManager.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);

        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
