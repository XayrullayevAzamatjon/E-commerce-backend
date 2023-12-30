package com.ecommerce.model;

public record ProductDto(Long id,String name,String imageUrl,Double price,String description,Long categoryId) {
}
