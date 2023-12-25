package com.ecommerse.service;


import com.ecommerse.entity.CategoryEntity;
import com.ecommerse.model.CategoryCreateRequest;
import com.ecommerse.model.mapper.CategoryMapper;
import com.ecommerse.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
  //  private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
       // this.categoryMapper = CategoryMapper.INSTANCE;
    }
    public Long create(CategoryEntity request){
     //   CategoryEntity newCategory = categoryMapper.toEntity(request);
        CategoryEntity saved = categoryRepository.save(request);
        return  saved.getId();
    }
    public CategoryEntity findById(Long id){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if (categoryEntity.isPresent()){
            return categoryEntity.get();
        }
        else {
            throw new RuntimeException("Category is not found !");
        }
    }

    public List<CategoryEntity> findAll(){
        return categoryRepository.findAll();
    }
}
