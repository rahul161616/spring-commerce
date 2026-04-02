package com.jugger.springcommerce.modules.cart.mapper;

import com.jugger.springcommerce.modules.cart.dto.CartItemPublicResponse;
import com.jugger.springcommerce.modules.cart.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemPublicMapper {
    public CartItemPublicResponse mapToPublicResponse(CartItem cartItem) {
        return CartItemPublicResponse.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct() != null ? cartItem.getProduct().getId() : null)
                .productName(cartItem.getProductNameSnapshot())
                .productSlug(cartItem.getProductSlugSnapshot())
                .quantity(cartItem.getQuantity())
                .currencyCode(cartItem.getCurrencyCode())
                .unitPrice(cartItem.getUnitPriceSnapshot())
                .compareAt(cartItem.getCompareAtSnapshot())
                .lineSubtotal(cartItem.getLineSubtotalAmount())
                .lineDiscount(cartItem.getLineDiscountAmount())
                .lineTotal(cartItem.getLineTotalAmount())
                .build();
    }
}
