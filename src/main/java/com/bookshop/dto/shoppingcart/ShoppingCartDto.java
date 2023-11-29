package com.bookshop.dto.shoppingcart;

import java.util.Set;

public record ShoppingCartDto(
        Long id,
        Long userId,
        Set<CartItemDto> cartItems) {
}
