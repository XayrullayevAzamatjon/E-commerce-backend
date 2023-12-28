package com.ecommerse.service;


import com.ecommerse.entity.CategoryEntity;
import com.ecommerse.entity.ProductEntity;
import com.ecommerse.model.ProductDto;
import com.ecommerse.repository.CategoryRepository;
import com.ecommerse.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductService(final ProductRepository productRepository,
                          final CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Long create(ProductDto product) {
        try {
            Optional<CategoryEntity> categoryOptional = categoryRepository.findById(product.categoryId());
            if (categoryOptional.isPresent()) {
                CategoryEntity category = categoryOptional.get();
                ProductEntity savedProduct = productRepository.save(toEntity(product, category));
                return savedProduct.getId();
            } else {
                throw new RuntimeException("Category not found for id: " + product.categoryId());
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while creating a product: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create a new product.", e);
        }
    }

    public List<ProductDto> findAll() {
        try {
            return productRepository.findAll().stream().map(this::toDto).toList();
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching all products: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve products.", e);
        }
    }
    public ProductDto findById(Long id) {
        try {
            Optional<ProductEntity> foundProduct = productRepository.findById(id);
            if (foundProduct.isPresent()) {
                return toDto(foundProduct.get());
            } else {
                LOGGER.warn("Product not found for id: {}", id);
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while finding a product by id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to find a product.", e);
        }
    }


    public ProductDto toDto(ProductEntity entity) {
        return new ProductDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getCategory().getId()
        );
    }
    private ProductEntity toEntity(ProductDto productDto, CategoryEntity category) {
        if (productDto == null) {
            return null;
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDto.id());
        productEntity.setImageUrl(productDto.imageUrl());
        productEntity.setDescription(productDto.description());
        productEntity.setCategory(category);
        productEntity.setName(productDto.name());
        productEntity.setPrice(productDto.price());

        return productEntity;
    }

    public Long update(Long id, ProductDto product) {
        try {
            Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
            if (productEntityOptional.isPresent()) {
                ProductEntity entity = productEntityOptional.get();
                entity.setName(product.name());
                entity.setPrice(product.price());
                entity.setDescription(product.description());
                entity.setImageUrl(product.imageUrl());
                ProductEntity updatedEntity = productRepository.save(entity);
                LOGGER.info("Product with id {} updated successfully.", id);
                return updatedEntity.getId();
            } else {
                LOGGER.warn("Product not found for id: {}", id);
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while updating a product with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update the product.", e);
        }
    }

}
