package com.bookshop.mapper;

import com.bookshop.dto.orderitem.OrderItemDto;
import com.bookshop.model.CartItem;
import com.bookshop.model.OrderItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl"
)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "price", source = "book.price")
    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItem(CartItem cartItem);
}
