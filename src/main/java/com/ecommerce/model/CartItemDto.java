package com.ecommerce.model;

import com.ecommerce.entity.ProductEntity;

public record CartItemDto(Long id, Integer quantity, ProductEntity product) {
}
