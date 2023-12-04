package com.bookshop.service;

import com.bookshop.dto.order.CreateOrderRequestDto;
import com.bookshop.dto.order.OrderDto;
import com.bookshop.dto.order.UpdateOrderStatusDto;
import com.bookshop.dto.orderitem.OrderItemDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    List<OrderDto> getAllById(Pageable pageable, Long id);

    OrderDto createOrder(Long id, CreateOrderRequestDto requestDto);

    OrderDto update(Long orderId, UpdateOrderStatusDto updateDto);

    List<OrderItemDto> getItemsByOrderId(Pageable pageable, Long orderId);

    OrderItemDto getItemByOrderIdAndItemId(Long orderId, Long itemId);
}
