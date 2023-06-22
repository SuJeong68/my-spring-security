package com.sudang.myspringsecurity.repository;

import com.sudang.myspringsecurity.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private final JpaTokenRepository repository;

    // 새 토큰 생성
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    // 특정 클라이언트를 위해 생성된 토큰을 저장
    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = repository.findTokenByIdentifier(identifier);

        if (existingToken.isPresent()) {
            // ID 존재 시 새로 생성된 값으로 토큰의 값을 업데이트
            Token token = existingToken.get();
            token.changeToken(csrfToken.getToken());
        } else {
            Token token = Token.builder()
                    .identifier(identifier)
                    .token(csrfToken.getToken())
                    .build();
            repository.save(token);
        }
    }

    // 토큰 세부 정보가 있으면 로드, 아니면 null
    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = repository.findTokenByIdentifier(identifier);

        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            return new DefaultCsrfToken(
                    "X-CSRF-TOKEN",
                    "_csrf",
                    token.getToken()
            );
        }
        return null;
    }
}
