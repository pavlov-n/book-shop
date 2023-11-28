package com.bookshop.mapper;

import com.bookshop.dto.shoppingcart.CartItemDto;
import com.bookshop.dto.shoppingcart.CreateCartItemRequestDto;
import com.bookshop.model.CartItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = BookMapper.class
)
public interface CartItemMapper {
    CartItem toCartItem(CreateCartItemRequestDto requestDto);

    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "book.id", target = "bookId")
    CartItemDto toDto(CartItem cartItem);
}
