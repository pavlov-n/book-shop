package com.bookshop.dto.orderitem;

public record OrderItemDto(
        Long id,
        Long bookId,
        int quantity
) {
}
