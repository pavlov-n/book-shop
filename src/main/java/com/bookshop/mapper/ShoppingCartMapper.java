package com.bookshop.mapper;

import com.bookshop.dto.shoppingcart.CreateCartItemRequestDto;
import com.bookshop.dto.shoppingcart.ShoppingCartDto;
import com.bookshop.dto.shoppingcart.UpdateCartItemRequestDto;
import com.bookshop.model.ShoppingCart;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = CartItemMapper.class
)
public interface ShoppingCartMapper {
    @Mapping(source = "cartItems", target = "cartItems")
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCartDto toSaveResponseDto(CreateCartItemRequestDto requestDto);

    ShoppingCartDto toUpdateResponseDto(UpdateCartItemRequestDto requestDto);
}
