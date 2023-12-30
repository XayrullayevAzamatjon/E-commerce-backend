package com.ecommerce.api.v1;


import com.ecommerce.model.ProductDto;
import com.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public Long create(@RequestBody ProductDto product){
        return productService.create(product);
    }
    @GetMapping
    public List<ProductDto> findAll(){
        return productService.findAll();
    }
    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id){
        return productService.findById(id);
    }
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id,@RequestBody ProductDto product){
        return productService.update(id,product);
    }

}
