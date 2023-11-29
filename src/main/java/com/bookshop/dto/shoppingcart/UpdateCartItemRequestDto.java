package com.bookshop.dto.shoppingcart;

import jakarta.validation.constraints.Positive;

public record UpdateCartItemRequestDto(
        @Positive
        int quantity) {
}
