package com.jugger.springcommerce.modules.cart.repository;

import com.jugger.springcommerce.modules.cart.enums.CartStatus;
import com.jugger.springcommerce.modules.cart.model.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = {"items", "items.product"})
    Optional<Cart> findBySessionIdAndStatus(String sessionId, CartStatus status);
}
