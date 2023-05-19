package com.sudang.myspringsecurity.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sudang
 * @date   2023/05/19
**/
@RestController
@Async
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }

    @GetMapping("/ciao")
    public String ciao() {
        Callable<String> task = () -> {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            return securityContext.getAuthentication().getName();
        };

        // ExecutorService 는 Callable 을 Job 으로 등록하고 수행시킬 수 있음 -> Thread pool 관련
        ExecutorService e = Executors.newCachedThreadPool();
        try {
            // SecurityContextHolder 의 MODE 설정 후 사용
            var contextTask = new DelegatingSecurityContextCallable<>(task);
            return "Ciao, " + e.submit(contextTask).get() + "!";
        } catch (ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            e.shutdown();
        }
    }

    @GetMapping("/hola")
    public String hola() {
        Callable<String> task = () -> {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            return securityContext.getAuthentication().getName();
        };

        ExecutorService e = Executors.newCachedThreadPool();
        // Thread pool 에 작업을 제출하는 방식
        e = new DelegatingSecurityContextExecutorService(e);
        try {
            return "Hola, " + e.submit(task).get() + "!";
        } catch (ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            e.shutdown();
        }
    }
}
