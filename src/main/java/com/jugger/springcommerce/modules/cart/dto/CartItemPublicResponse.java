package com.jugger.springcommerce.modules.cart.dto;

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
public class CartItemPublicResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String productSlug;
    private Integer quantity;
    private String currencyCode;
    private BigDecimal unitPrice;
    private BigDecimal compareAt;
    private BigDecimal lineSubtotal;
    private BigDecimal lineDiscount;
    private BigDecimal lineTotal;
}
