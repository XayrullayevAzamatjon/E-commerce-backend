package com.ecommerse.model;

import org.springframework.lang.NonNull;

public record AddToCartDto(Long id, @NonNull Long productId, @NonNull Integer quantity) {
}
