package com.ecommerse.service;


import com.ecommerse.entity.AuthenticationTokenEntity;
import com.ecommerse.entity.UserEntity;
import com.ecommerse.repository.AuthenticationTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {
    private final AuthenticationTokenRepository tokenRepository;

    public AuthenticationTokenService(AuthenticationTokenRepository authenticationTokenRepository) {
        this.tokenRepository = authenticationTokenRepository;
    }

    public void saveConfirmationToken(AuthenticationTokenEntity tokenEntity) {
        tokenRepository.save(tokenEntity);
    }

    public AuthenticationTokenEntity getToken(UserEntity user) {
        return tokenRepository.findByUser(user);
    }
}
