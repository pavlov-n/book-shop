package com.bookshop.repository;

import com.bookshop.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"user", "cartItems", "cartItems.book"})
    Optional<ShoppingCart> findByUserId(Long id);
}
