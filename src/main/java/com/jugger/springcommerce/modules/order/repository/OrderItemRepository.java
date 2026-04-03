package com.jugger.springcommerce.modules.order.repository;

import com.jugger.springcommerce.modules.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
