package com.ecommerse.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "wishlist")
public class WishListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = UserEntity.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(nullable = false,name = "product_id")
    private ProductEntity product;

    private Date createdDate;

    public WishListEntity(UserEntity user, ProductEntity product) {
        this.user=user;
        this.product=product;
        this.createdDate=new Date();
    }

    public WishListEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
