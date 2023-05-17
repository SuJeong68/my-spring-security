package com.sudang.myspringsecurity.service;


import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 가장 간단한 PasswordEncoder 계약 구현 실습
 *
 * @author sudang
 * @date   2023/05/17
**/
public class PlainTextPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
