package com.ecommerse.repository;

import com.ecommerse.entity.AuthenticationTokenEntity;
import com.ecommerse.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationTokenEntity,Long> {
    AuthenticationTokenEntity findByUser(UserEntity user);
    AuthenticationTokenEntity findByToken(String token);
}
