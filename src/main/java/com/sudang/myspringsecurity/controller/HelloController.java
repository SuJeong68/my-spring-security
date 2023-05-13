package com.sudang.myspringsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. curl http://localhost:8080/hello
 * 2. curl -u user:[security password] http://localhost:8080/hello <- 자격 증명
 *
 * @author sudang
 * @date   2023/05/13
**/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
