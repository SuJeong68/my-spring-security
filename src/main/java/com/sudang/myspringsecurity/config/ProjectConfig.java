package com.sudang.myspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .authorities("READ")
                .build();
        var user2 = User.withUsername("achi")
                .password("1234")
                .authorities("WRITE")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // 권한 부여 구성 추가
    // WebSecurityConfigurerAdapter 가 Deprecated 된 후 사용되는 방법
    // formLogin 방식과 httpBasic 방식 존재
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .anyRequest().hasAuthority("WRITE");
                // 위 코드와 동일하게 동작하지만 spEL을 사용하므로 구문이 복잡해짐.
                // .anyRequest().access("hasAuthority('WRITE')");
                // access()는 hasAuthority(), hasAnyAuthority()를 사용할 수 없거나 아래와 같이 범용적인 권한 부여가 필요할 때만 사용함.
                // .anyRequest().access("hasAuthority('READ') and !hasAuthority('delete')");

        return http.build();
    }
}
