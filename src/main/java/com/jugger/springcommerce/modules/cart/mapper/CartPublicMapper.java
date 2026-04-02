package com.jugger.springcommerce.modules.cart.mapper;

import com.jugger.springcommerce.modules.cart.dto.CartPublicResponse;
import com.jugger.springcommerce.modules.cart.enums.CartItemStatus;
import com.jugger.springcommerce.modules.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartPublicMapper {
    private final CartItemPublicMapper cartItemPublicMapper;

    public CartPublicResponse mapToPublicResponse(Cart cart) {
        return CartPublicResponse.builder()
                .id(cart.getId())
                .sessionId(cart.getSessionId())
                .currencyCode(cart.getCurrencyCode())
                .status(cart.getStatus())
                .itemCount(cart.getItemCount())
                .subtotalAmount(cart.getSubtotalAmount())
                .discountAmount(cart.getDiscountAmount())
                .taxAmount(cart.getTaxAmount())
                .shippingAmount(cart.getShippingAmount())
                .grandTotalAmount(cart.getGrandTotalAmount())
                .items(cart.getItems().stream()
                        .filter(item -> item.getStatus() == CartItemStatus.ACTIVE)
                        .map(cartItemPublicMapper::mapToPublicResponse)
                        .toList())
                .build();
    }
}