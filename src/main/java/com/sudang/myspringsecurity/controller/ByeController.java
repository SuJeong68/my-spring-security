package com.sudang.myspringsecurity.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ByeController {

    // 메서드가 별도로 스레드에서 실행
    @Async
    @GetMapping("/bye")
    public String goodbye() {
        SecurityContext context = SecurityContextHolder.getContext();
        // NullPointerException 발생 => 서로 다른 쓰레드에서 돌아가기 때문에 Authentication 객체가 null
        String username = context.getAuthentication().getName();

        return "Good Bye, " + username + "!";
    }
}
