package com.ecommerse.model;

public record ProductDto(Long id,String name,String imageUrl,Double price,String description,Long categoryId) {
}
