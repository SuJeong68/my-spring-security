package com.sudang.myspringsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class ProjectConfig {

    private final CsrfTokenRepository customCsrfTokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        // c = CsrfConfigurer
        http.csrf(c -> {
            c.csrfTokenRepository(customCsrfTokenRepository);
            c.ignoringAntMatchers("/ciao");
        });

        http.authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
