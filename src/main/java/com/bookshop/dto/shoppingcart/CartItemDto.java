package com.bookshop.dto.shoppingcart;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        Long quantity) {
}
