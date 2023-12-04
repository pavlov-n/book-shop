package com.bookshop.service.impl;

import com.bookshop.dto.order.CreateOrderRequestDto;
import com.bookshop.dto.order.OrderDto;
import com.bookshop.dto.order.UpdateOrderStatusDto;
import com.bookshop.dto.orderitem.OrderItemDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.OrderItemMapper;
import com.bookshop.mapper.OrderMapper;
import com.bookshop.model.Order;
import com.bookshop.model.OrderItem;
import com.bookshop.model.ShoppingCart;
import com.bookshop.repository.OrderItemRepository;
import com.bookshop.repository.OrderRepository;
import com.bookshop.repository.ShoppingCartRepository;
import com.bookshop.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderDto> getAllById(Pageable pageable, Long id) {
        return orderRepository.findAllByUserId(pageable, id).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public OrderDto createOrder(Long id, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart is empty")
        );
        Order order = orderMapper.toOrder(shoppingCart, requestDto);
        orderRepository.save(order);
        shoppingCart.clear();
        return orderMapper.toDto(order);
    }

    @Transactional
    @Override
    public OrderDto update(Long orderId,
                           UpdateOrderStatusDto updateDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find order by id " + orderId)
        );
        order.setStatus(updateDto.status());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getItemsByOrderId(Pageable pageable, Long orderId) {
        return orderItemRepository.findAllByOrderId(pageable, orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getItemByOrderIdAndItemId(Long orderId,
                                                  Long itemId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find item by id %d in order by id: %d".formatted(itemId, orderId))
                );
        return orderItemMapper.toDto(orderItem);
    }
}
