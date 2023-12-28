package com.ecommerse.model;

import com.ecommerse.entity.ProductEntity;

public record CartItemDto(Long id, Integer quantity, ProductEntity product) {
}
