package com.sudang.myspringsecurity.service;

import com.sudang.myspringsecurity.model.User;
import com.sudang.myspringsecurity.repository.UserRepository;
import com.sudang.myspringsecurity.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 예외 인스턴스를 만들기 위한 공급자
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Problem during authentication");

        User user = userRepository.findUserByUsername(username).orElseThrow(s);
        // domain.User 를 CustomUserDetails 데코레이터로 래핑하고 반환
        return new CustomUserDetails(user);
    }
}
