package com.ecommerce.service;


import com.ecommerce.common.ApiResponse;
import com.ecommerce.entity.CartEntity;
import com.ecommerce.entity.ProductEntity;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.exceptions.AuthenticationFailedException;
import com.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.model.AddToCartDto;
import com.ecommerce.model.CartDto;
import com.ecommerce.model.CartItemDto;
import com.ecommerce.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public ApiResponse delete(Long itemId, String token) {
        try {
            tokenService.authenticate(token);
            UserEntity user = tokenService.getUser(token);
            Optional<CartEntity> cartEntityOptional = cartRepository.findById(itemId);
            if (cartEntityOptional.isEmpty()) {
                return new ApiResponse("Error", "Cart ID is not valid [" + itemId + "]");
            }
            CartEntity cart = cartEntityOptional.get();
            if (!cart.getUser().equals(user)) {
                return new ApiResponse("Error", "Cart Item does not belong to user with id [" + user.getId() + "]");
            }
            cartRepository.delete(cart);
            return new ApiResponse("Success", "Cart item deleted successfully.");
        } catch (AuthenticationFailedException e) {
            LOGGER.warn("Authentication failed for token '{}': {}", token, e.getMessage());
            return new ApiResponse("Error", "Authentication failed. Invalid or expired token.");
        } catch (Exception e) {
            LOGGER.error("Error occurred while deleting cart item: {}", e.getMessage(), e);
            return new ApiResponse("Error", "Failed to delete cart item. Please try again later.");
        }
    }
}
