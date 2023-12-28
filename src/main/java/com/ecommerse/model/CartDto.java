package com.ecommerse.model;

import java.util.List;

public record CartDto(List<CartItemDto> cartItems,Double totalCost) {
}
