package com.jugger.springcommerce.modules.order.mapper;

import com.jugger.springcommerce.modules.order.dto.OrderPublicResponse;
import com.jugger.springcommerce.modules.order.enums.OrderStatus;
import com.jugger.springcommerce.modules.order.model.Order;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderPublicMapper {
    public OrderPublicResponse mapToPublicResponse(Order savedOrder) {
        return OrderPublicResponse.builder()
                .id(savedOrder.getId())
                .orderCode(savedOrder.getOrderCode())
                .sessionId(savedOrder.getSessionId())
                .customerName(savedOrder.getCustomerName())
                .customerEmail(savedOrder.getCustomerEmail())
                .customerPhone(savedOrder.getCustomerPhone())
                .currencyCode(savedOrder.getCurrencyCode())
                .subtotalAmount(savedOrder.getSubtotalAmount())
                .discountAmount(savedOrder.getDiscountAmount())
                .taxAmount(savedOrder.getTaxAmount())
                .shippingAmount(savedOrder.getShippingAmount())
                .grandTotalAmount(savedOrder.getGrandTotalAmount())
                .status(savedOrder.getStatus())
                .itemCount(savedOrder.getItems() != null ? savedOrder.getItems().stream().mapToInt(item -> item.getQuantity() != null ? item.getQuantity() : 0).sum() : 0)
                .notes(savedOrder.getNotes())
                .build();
    }

    public OrderPublicResponse mapOrderByCodeToPublicResponse(ResultSet rs) throws SQLException {
        return OrderPublicResponse.builder()
                .id(rs.getLong("id"))
                .orderCode(rs.getString("order_code"))
                .sessionId(rs.getString("session_id"))
                .customerName(rs.getString("customer_name"))
                .customerEmail(rs.getString("customer_email"))
                .customerPhone(rs.getString("customer_phone"))
                .currencyCode(rs.getString("currency_code"))
                .subtotalAmount(rs.getBigDecimal("subtotal_amount"))
                .discountAmount(rs.getBigDecimal("discount_amount"))
                .taxAmount(rs.getBigDecimal("tax_amount"))
                .shippingAmount(rs.getBigDecimal("shipping_amount"))
                .grandTotalAmount(rs.getBigDecimal("grand_total_amount"))
                .status(OrderStatus.valueOf(rs.getString("status")))
                .itemCount(0)
                .notes(rs.getString("notes"))
                .build();
    }
}
