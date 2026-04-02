package com.jugger.springcommerce.modules.cart.service;

import com.jugger.springcommerce.modules.cart.dto.CartItemPublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartItemUpdatePublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartPublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartPublicResponse;

public interface CartService {
    CartPublicResponse getActiveCart(String sessionId);
    CartPublicResponse createOrGetActiveCart(CartPublicRequest request);
    CartPublicResponse addItemToCart(CartItemPublicRequest request);
    CartPublicResponse updateCartItemQuantity(Long cartItemId, CartItemUpdatePublicRequest request);
    CartPublicResponse deleteCartItem(Long cartItemId);
}
