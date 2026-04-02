package com.jugger.springcommerce.modules.cart.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemUpdatePublicRequest {
    public Integer quantity;
}
