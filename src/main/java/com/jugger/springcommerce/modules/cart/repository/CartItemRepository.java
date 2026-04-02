package com.jugger.springcommerce.modules.cart.repository;

import com.jugger.springcommerce.modules.cart.model.CartItem;
import com.jugger.springcommerce.modules.cart.model.Cart;
import com.jugger.springcommerce.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
