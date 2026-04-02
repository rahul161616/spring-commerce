package com.jugger.springcommerce.modules.cart.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.cart.dto.CartItemPublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartItemUpdatePublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartPublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartPublicResponse;
import com.jugger.springcommerce.modules.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.PUBLIC_CART_API)
public class CartPublicController {
    private final CartService cartService;

    @GetMapping("/{sessionId}")
    public ResponseEntity<CartPublicResponse> getActiveCart(@PathVariable String sessionId) {
        return ResponseEntity.ok(cartService.getActiveCart(sessionId));
    }

    @PostMapping
    public ResponseEntity<CartPublicResponse> createOrGetActiveCart(@RequestBody CartPublicRequest request) {
        return ResponseEntity.ok(cartService.createOrGetActiveCart(request));
    }

    @PostMapping("/items")
    public ResponseEntity<CartPublicResponse> addItemToCart(@RequestBody CartItemPublicRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(request));
    }

    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<CartPublicResponse> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody CartItemUpdatePublicRequest request
    ) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(cartItemId, request));
    }
    @DeleteMapping("/items/{cartItemId}/remove")
    public ResponseEntity<CartPublicResponse> deleteCartItem(
            @PathVariable Long cartItemId
    ) {
        return ResponseEntity.ok(cartService.deleteCartItem(cartItemId));
    }
}
