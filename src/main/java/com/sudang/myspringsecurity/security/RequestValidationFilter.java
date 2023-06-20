package com.sudang.myspringsecurity.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        String requestId = httpRequest.getHeader("Request-Id");

        if (requestId == null || requestId.isBlank()) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            // 헤더가 없으면 HTTP 상태가 '400 잘못된 요청'으로 바뀌고 요청이 다음 필터로 넘어가지 않음
            return;
        }

        // 헤더가 있으면 요청을 필터 체인의 다음 필터로 전달
        filterChain.doFilter(request, response);
    }
}
