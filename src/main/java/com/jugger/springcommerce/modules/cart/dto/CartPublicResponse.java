package com.jugger.springcommerce.modules.cart.dto;

import com.jugger.springcommerce.modules.cart.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartPublicResponse {
    private Long id;
    private String sessionId;
    private String currencyCode;
    private CartStatus status;
    private Integer itemCount;
    private BigDecimal subtotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal shippingAmount;
    private BigDecimal grandTotalAmount;
    private List<CartItemPublicResponse> items;
}
