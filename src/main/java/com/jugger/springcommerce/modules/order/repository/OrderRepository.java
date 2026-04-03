package com.jugger.springcommerce.modules.order.repository;

import com.jugger.springcommerce.modules.order.model.Order;
import com.jugger.springcommerce.modules.order.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface OrderRepository extends JpaRepository <Order,Long>{
    Optional<Order> findByOrderCode(String orderCode);
    Optional<Order> findTopBySessionIdAndStatusInOrderByIdDesc(String sessionId, Collection<OrderStatus> statuses);
}
