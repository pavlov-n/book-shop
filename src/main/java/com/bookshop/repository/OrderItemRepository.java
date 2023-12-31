package com.bookshop.repository;

import com.bookshop.model.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Pageable pageable, Long orderId);

    Optional<OrderItem> findByIdAndOrderId(Long id, Long orderId);
}
