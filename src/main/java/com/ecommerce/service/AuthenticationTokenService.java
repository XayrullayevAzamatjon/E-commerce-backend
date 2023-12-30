package com.ecommerce.service;


import com.ecommerce.entity.AuthenticationTokenEntity;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.exceptions.AuthenticationFailedException;
import com.ecommerce.repository.AuthenticationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    public UserEntity getUser(String token){
        AuthenticationTokenEntity authenticationToken = tokenRepository.findByToken(token);
        if (Objects.isNull(authenticationToken)){
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token){
        if (Objects.isNull(token)){
            throw  new AuthenticationFailedException("Token is not present");
        }
        if(Objects.isNull(getUser(token))){
            throw  new AuthenticationFailedException("Token is not valid");
        }
    }

}
