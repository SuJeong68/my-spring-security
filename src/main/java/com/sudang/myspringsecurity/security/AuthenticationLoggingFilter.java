package com.sudang.myspringsecurity.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
// 해당 필터가 두 번 이상 호출되지 않도록 처리하기 위해 OncePerRequestFilter 사용
public class AuthenticationLoggingFilter extends OncePerRequestFilter {

    // OncePerRequestFilter 는 HTTP 필터만 지원
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestId = request.getHeader("Request-Id");

        log.info("Successfully authenticated request with id " + requestId);

        filterChain.doFilter(request, response);
    }
}
