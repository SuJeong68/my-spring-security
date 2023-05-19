package com.sudang.myspringsecurity.config;

import com.sudang.myspringsecurity.security.CustomAuthenticationFailureHandler;
import com.sudang.myspringsecurity.security.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * @author sudang
 * @date 2023/05/14
 **/
// Spring security 5.7 이상에서 WebSecurityConfigurerAdapter 사용을 권장하지 않기 때문에
// SecurityFilterChain 사용하는 EnableWebSecurity 로 변경
@EnableWebSecurity(debug = true)
@Configuration
public class ProjectConfig {

//    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
//    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
//
//    public ProjectConfig(CustomAuthenticationSuccessHandler authenticationSuccessHandler, CustomAuthenticationFailureHandler authenticationFailureHandler) {
//        this.authenticationSuccessHandler = authenticationSuccessHandler;
//        this.authenticationFailureHandler = authenticationFailureHandler;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .defaultSuccessUrl("/home", true)
                .and()
            .build();
    }

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
