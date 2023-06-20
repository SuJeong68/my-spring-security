package com.sudang.myspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("yang")
                .password("1234")
                .roles("ADMIN")
                .build();
        var user2 = User.withUsername("achi")
                .password("1234")
                .roles("MANAGER")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                // curl -u yang:1234 -XGET http://localhost:8080/a/b <- 이 요청은 403 Forbidden
                .mvcMatchers(HttpMethod.GET, "/a").authenticated()
                // '/a/b'가 붙은 모든 경로에 적용
                .mvcMatchers("/a/b/**").hasRole("ADMIN")
                .mvcMatchers("/product/{code:^[0-9]*$}").permitAll()
                .mvcMatchers(HttpMethod.POST, "/a").permitAll()
                .anyRequest().denyAll();

        // CSRF 는 취약점이지만 간단한 실습과 POST, PUT, DELETE 로 노출된 엔드포인트를 포함애 호출할 수 있도록 비활성화
        http.csrf().disable();

        return http.build();
    }
}
