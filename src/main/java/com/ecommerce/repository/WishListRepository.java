package com.ecommerce.repository;

import com.ecommerce.entity.ProductEntity;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity,Long> {
    boolean existsByUserAndProduct(UserEntity user, ProductEntity product);
    List<WishListEntity> findAllByUserOrderByCreatedDateDesc(UserEntity user);
}
