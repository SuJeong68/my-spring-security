package com.sudang.myspringsecurity.config;

import com.sudang.myspringsecurity.security.CustomAuthenticationFailureHandler;
import com.sudang.myspringsecurity.security.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author sudang
 * @date 2023/05/14
 **/
// Spring security 5.7 이상에서 WebSecurityConfigurerAdapter 사용을 권장하지 않기 때문에
// SecurityFilterChain 사용하는 EnableWebSecurity 로 변경
@EnableWebSecurity(debug = true)
@Configuration
public class ProjectConfig {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    public ProjectConfig(CustomAuthenticationSuccessHandler authenticationSuccessHandler, CustomAuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
            .build();
    }
}
