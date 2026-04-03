package com.jugger.springcommerce.modules.order.dto;

import com.jugger.springcommerce.modules.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPublicRequest {
    private String sessionId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String currencyCode;
    private BigDecimal subtotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal shippingAmount;
    private BigDecimal grandTotalAmount;
    private OrderStatus status;
    private String notes;
}
