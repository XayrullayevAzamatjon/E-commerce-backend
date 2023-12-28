package com.ecommerse.service;


import com.ecommerse.common.ApiResponse;
import com.ecommerse.entity.CartEntity;
import com.ecommerse.entity.ProductEntity;
import com.ecommerse.entity.UserEntity;
import com.ecommerse.exceptions.AuthenticationFailedException;
import com.ecommerse.exceptions.ProductNotFoundException;
import com.ecommerse.model.AddToCartDto;
import com.ecommerse.model.CartDto;
import com.ecommerse.model.CartItemDto;
import com.ecommerse.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final AuthenticationTokenService tokenService;
    private final ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);


    public CartService(CartRepository cartRepository, AuthenticationTokenService tokenService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.tokenService = tokenService;
        this.productService = productService;
    }

    public ApiResponse addToCart(AddToCartDto addToCartDto, String token) {
        try {
            // Authenticate the token and get the user
            tokenService.authenticate(token);
            UserEntity user = tokenService.getUser(token);

            // Retrieve the product based on the provided productId
            ProductEntity product = productService.getById(addToCartDto.productId());

            // Create a new CartEntity
            CartEntity cart = new CartEntity();
            cart.setProduct(product);
            cart.setUser(user);
            cart.setQuantity(addToCartDto.quantity());
            cart.setCreatedDate(new Date());

            // Save the cart to the repository
            cartRepository.save(cart);

            LOGGER.info("Product '{}' added to the cart for user '{}'", product.getName(), user.getEmail());

            return new ApiResponse("Success", "Product added to the cart successfully.");
        } catch (AuthenticationFailedException e) {
            LOGGER.warn("Authentication failed for token '{}': {}", token, e.getMessage());
            return new ApiResponse("Error", "Authentication failed. Invalid or expired token.");
        } catch (ProductNotFoundException e) {
            LOGGER.warn("Product not found for ID '{}': {}", addToCartDto.productId(), e.getMessage());
            return new ApiResponse("Error", "Product not found. Please provide a valid product ID.");
        } catch (Exception e) {
            LOGGER.error("Error occurred while adding to cart: {}", e.getMessage(), e);
            return new ApiResponse("Error", "Failed to add product to the cart. Please try again later.");
        }
    }

    public CartDto listCart(String token) {
        try {
            // Authenticate the token and get the user
            tokenService.authenticate(token);
            UserEntity user = tokenService.getUser(token);

            // Retrieve the cart items for the user from the repository
            List<CartEntity> cartEntities = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

            // Map CartEntity objects to CartItemDto objects
            List<CartItemDto> cartItems = cartEntities.stream()
                    .map(this::mapToCartItemDto)
                    .toList();
            Double totalCost = cartEntities.stream()
                    .mapToDouble(cartEntity -> cartEntity.getProduct().getPrice() * cartEntity.getQuantity())
                    .sum();
            return new CartDto(cartItems,totalCost);
        } catch (AuthenticationFailedException e) {
            LOGGER.warn("Authentication failed for token '{}': {}", token, e.getMessage());
            throw new RuntimeException("Authentication failed. Invalid or expired token.");
        } catch (Exception e) {
            LOGGER.error("Error occurred while listing the cart: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to list the cart. Please try again later.");
        }
    }

    private CartItemDto mapToCartItemDto(CartEntity cartEntity) {
        return new CartItemDto(
                cartEntity.getId(),
                cartEntity.getQuantity(),
                cartEntity.getProduct()
        );
    }

}