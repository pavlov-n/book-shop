package com.bookshop.service;

import com.bookshop.dto.shoppingcart.CreateCartItemRequestDto;
import com.bookshop.dto.shoppingcart.ShoppingCartDto;
import com.bookshop.dto.shoppingcart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto findById(Long id);

    ShoppingCartDto addToCart(Long id, CreateCartItemRequestDto requestDto);

    ShoppingCartDto update(Long userId, Long cartItemId, UpdateCartItemRequestDto quantityDto);

    ShoppingCartDto removeCartItem(Long userId, Long cartItemId);
}
