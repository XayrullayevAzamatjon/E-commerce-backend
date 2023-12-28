package com.ecommerse.api.v1;

import com.ecommerse.common.ApiResponse;
import com.ecommerse.entity.ProductEntity;
import com.ecommerse.model.ProductDto;
import com.ecommerse.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishListController {
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody ProductEntity product, @RequestParam String token){
        ApiResponse apiResponse = wishListService.addToWishlist(token, product);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getAllWishList(@PathVariable String token){
        return new ResponseEntity<>( wishListService.getAllWishList(token),HttpStatus.OK);
    }
}
