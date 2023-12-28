package com.ecommerse.repository;

import com.ecommerse.entity.CartEntity;
import com.ecommerse.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    List<CartEntity> findAllByUserOrderByCreatedDateDesc(UserEntity user);
}
