package com.ecommerse.service;

import com.ecommerse.entity.CategoryEntity;
import com.ecommerse.model.CategoryCreateRequest;
import com.ecommerse.model.CategoryResponse;
import com.ecommerse.model.mapper.CategoryMapper;
import com.ecommerse.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = CategoryMapper.INSTANCE;
    }

    public Long create(CategoryCreateRequest request) {
        try {
            Optional<CategoryEntity> existingCategory = categoryRepository.findByCategoryName(request.categoryName());

            if (existingCategory.isPresent()) {
                LOGGER.warn("Category with name '{}' already exists. Cannot create a duplicate.", request.categoryName());
                throw new RuntimeException("Category with the same name already exists.");
            }
            CategoryEntity newCategory = categoryMapper.toEntity(request);
            CategoryEntity saved = categoryRepository.save(newCategory);
            return saved.getId();
        } catch (Exception e) {
            LOGGER.error("Error occurred while creating a category: {}", e.getMessage());
            throw new RuntimeException("Failed to create a new category.", e);
        }
    }


    public CategoryResponse findById(Long id) {
        try {
            Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
            return categoryEntity.map(categoryMapper::toResponse)
                    .orElseThrow(() -> new RuntimeException("Category not found for id: " + id));
        } catch (Exception e) {
            LOGGER.error("Error occurred while finding a category by id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to find a category.", e);
        }
    }

    public List<CategoryResponse> findAll() {
        try {
            return categoryRepository.findAll().stream()
                    .map(categoryMapper::toResponse)
                    .toList();
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching all categories: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve categories.", e);
        }
    }

    public Long update(Long id, CategoryCreateRequest request) {
        try {
            Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
            return categoryEntity.map(entity -> categoryRepository.save(categoryMapper.update(entity, request)).getId())
                    .orElseThrow(() -> new RuntimeException("Category not found for id: " + id));
        } catch (Exception e) {
            LOGGER.error("Error occurred while updating a category with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update the category.", e);
        }
    }
}
