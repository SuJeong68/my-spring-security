package com.sudang.myspringsecurity.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // 인증의 전체 논리를 나타냄
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Principal 인터페이스의 getName() 메서드를 Authentication 에서 상속받음
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        // UserDetailsService 및 PasswordEncoder를 호출해서 사용자 이름과 암호를 테스트
        if ("sudang".equals(username) && "12345".equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
        } else {
            throw new AuthenticationCredentialsNotFoundException("Error in authentication!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Authentication 형식의 구현을 추가함
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
