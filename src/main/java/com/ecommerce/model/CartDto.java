package com.ecommerce.model;

import java.util.List;

public record CartDto(List<CartItemDto> cartItems,Double totalCost) {
}
