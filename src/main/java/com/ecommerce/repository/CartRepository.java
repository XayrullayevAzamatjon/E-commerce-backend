package com.ecommerce.repository;

import com.ecommerce.entity.CartEntity;
import com.ecommerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    List<CartEntity> findAllByUserOrderByCreatedDateDesc(UserEntity user);
}
