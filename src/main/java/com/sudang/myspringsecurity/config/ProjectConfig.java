package com.sudang.myspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * UserDetailsService 빈에 대한 구성 클래스
 *
 * @author sudang
 * @date   2023/05/13
**/
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        // var 키워드는 구문을 간소하게 만들어주고 세부 정보를 감춤.
        // 메모리에 자격 증명을 저장하여 스프링 시큐리티가 요청을 인증할 때 사용할 수 있게 함.
        // 실행 시 콘솔에 자동 생성된 암호가 출력되지 않음.
        var userDetailsService = new InMemoryUserDetailsManager();

        // 주어진 사용자 이름, 암호, 권한목록으로 사용자 생성 후 UserDetailsService 에서 관리하도록 사용자 추가
        var user = User.withUsername("sudang")
                .password("12345")
                .authorities("read")
                .build();
        userDetailsService.createUser(user);

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 암호에 암호화나 해시를 적용하지 않고 일반 텍스트처럼 처리
        // NoOpPasswordEncoder는 암호를 비교할 때 간단한 문자열만 비교함.
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        // 모든 요청에 인증 필요함
        // http.authorizeRequests().anyRequest().authenticated();
        // 인증 없이 요청할 수 있음
        http.authorizeRequests().anyRequest().permitAll();
    }
}
