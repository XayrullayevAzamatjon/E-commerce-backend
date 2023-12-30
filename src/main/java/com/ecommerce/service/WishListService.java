package com.ecommerce.service;

import com.ecommerce.common.ApiResponse;
import com.ecommerce.entity.ProductEntity;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.entity.WishListEntity;
import com.ecommerce.exceptions.AuthenticationFailedException;
import com.ecommerce.model.ProductDto;
import com.ecommerce.repository.WishListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final AuthenticationTokenService tokenService;
    private final ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(WishListService.class);


    public WishListService(WishListRepository wishListRepository, AuthenticationTokenService tokenService, ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.tokenService = tokenService;
        this.productService = productService;
    }
    public ApiResponse addToWishlist(String token, ProductEntity product) {
        try {
            tokenService.authenticate(token);

            UserEntity user = tokenService.getUser(token);

            // Check if the product is already in the wishlist
            if (wishListRepository.existsByUserAndProduct(user, product)) {
                return new ApiResponse("Error", "Product is already in the wishlist.");
            }

            // Add the product to the wishlist
            WishListEntity wishList = new WishListEntity(user, product);
            wishListRepository.save(wishList);

            return new ApiResponse("Success", "Product added to the wishlist successfully.");
        } catch (AuthenticationFailedException e) {
            return new ApiResponse("Error", "Authentication failed. Invalid or expired token.");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            LOGGER.error("Error occurred while adding to wishlist: {}", e.getMessage(), e);
            return new ApiResponse("Error", "Failed to add product to wishlist. Please try again later.");
        }
    }
    public List<ProductDto> getAllWishList(String token) {
        try {
            tokenService.authenticate(token);

            UserEntity user = tokenService.getUser(token);
            List<WishListEntity> wishlists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);

            // Use Java streams for more concise code
            return wishlists.stream()
                    .map(wishlist -> productService.toDto(wishlist.getProduct())).toList();

        } catch (AuthenticationFailedException e) {
            LOGGER.warn("Authentication failed for token '{}': {}", token, e.getMessage());
            // You may want to throw an exception or handle it according to your requirements
            return Collections.emptyList();
        } catch (Exception e) {
            LOGGER.error("Error occurred while getting wishlist: {}", e.getMessage(), e);
            // You may want to throw an exception or handle it according to your requirements
            return Collections.emptyList();
        }
    }


}
