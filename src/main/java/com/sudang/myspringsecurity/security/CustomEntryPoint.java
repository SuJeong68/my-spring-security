package com.sudang.myspringsecurity.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증에 실패했을 때의 응답을 맞춤 구성함
 *
 * @author sudang
 * @date   2023/05/19
**/
public class CustomEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("message", "Hi, I'm Sudang!");
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }
}
