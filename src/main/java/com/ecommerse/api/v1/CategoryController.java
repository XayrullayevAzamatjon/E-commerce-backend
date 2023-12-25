package com.ecommerse.api.v1;

import com.ecommerse.entity.CategoryEntity;
import com.ecommerse.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ap1/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Long create(@RequestBody CategoryEntity entity){
       return categoryService.create(entity);
    }
    @GetMapping
    public List<CategoryEntity> findAll(){
        return categoryService.findAll();
    }
    @GetMapping("/{id}")
    public CategoryEntity findById(@PathVariable Long id){
        return categoryService.findById(id);
    }

}
