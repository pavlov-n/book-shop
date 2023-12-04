package com.bookshop.mapper;

import com.bookshop.dto.order.CreateOrderRequestDto;
import com.bookshop.dto.order.OrderDto;
import com.bookshop.model.CartItem;
import com.bookshop.model.Order;
import com.bookshop.model.ShoppingCart;
import java.math.BigDecimal;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = OrderItemMapper.class
)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderItems", source = "shoppingCart.cartItems")
    @Mapping(target = "total", source = "shoppingCart.cartItems", qualifiedByName = "totalPrice")
    Order toOrder(ShoppingCart shoppingCart, CreateOrderRequestDto orderRequestDto);

    @AfterMapping
    default void setOrderToItems(@MappingTarget Order order) {
        order.getOrderItems()
                .forEach(item -> item.setOrder(order));
    }

    @Named("totalPrice")
    default BigDecimal calculateTotalPrice(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::getTotalItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalItemPrice(CartItem cartItem) {
        BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
        return cartItem.getBook().getPrice().multiply(quantity);
    }
}
