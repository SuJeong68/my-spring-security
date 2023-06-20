package com.sudang.myspringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

// 현재 시나리오에 사용자의 개념이 존재하지 않음 = 자동구성 비활성화해도 됨
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class MySpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringSecurityApplication.class, args);
    }

}
