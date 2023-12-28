package com.ecommerse.api.v1;

import com.ecommerse.model.CategoryCreateRequest;
import com.ecommerse.model.CategoryResponse;
import com.ecommerse.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Long create(@RequestBody CategoryCreateRequest request){
       return categoryService.create(request);
    }
    @GetMapping
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }
    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Long id){
        return categoryService.findById(id);
    }
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody CategoryCreateRequest request){
        return categoryService.update(id,request);
    }

}
