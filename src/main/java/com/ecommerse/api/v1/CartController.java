package com.ecommerse.api.v1;

import com.ecommerse.common.ApiResponse;
import com.ecommerse.model.AddToCartDto;
import com.ecommerse.model.CartDto;
import com.ecommerse.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam String token){
        return new ResponseEntity<>(cartService.addToCart(addToCartDto,token), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<CartDto> getCartItems(@RequestParam String token){
        return new ResponseEntity<>(cartService.listCart(token),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,@RequestParam String token){
        return new ResponseEntity<>(cartService.delete(cartItemId,token),HttpStatus.OK);
    }
}
