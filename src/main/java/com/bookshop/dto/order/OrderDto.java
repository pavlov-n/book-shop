package com.bookshop.dto.order;

import com.bookshop.dto.orderitem.OrderItemDto;
import com.bookshop.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(
        Long id,
        Long userId,
        Set<OrderItemDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Status status
) {
}
