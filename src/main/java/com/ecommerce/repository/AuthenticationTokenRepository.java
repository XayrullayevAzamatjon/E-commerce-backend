package com.ecommerce.repository;

import com.ecommerce.entity.AuthenticationTokenEntity;
import com.ecommerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationTokenEntity,Long> {
    AuthenticationTokenEntity findByUser(UserEntity user);
    AuthenticationTokenEntity findByToken(String token);
}
