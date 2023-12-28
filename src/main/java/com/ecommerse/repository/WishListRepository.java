package com.ecommerse.repository;

import com.ecommerse.entity.ProductEntity;
import com.ecommerse.entity.UserEntity;
import com.ecommerse.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity,Long> {
    boolean existsByUserAndProduct(UserEntity user, ProductEntity product);
    List<WishListEntity> findAllByUserOrderByCreatedDateDesc(UserEntity user);
}
