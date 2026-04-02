package com.jugger.springcommerce.modules.cart.service.impl;

import com.jugger.springcommerce.common.exception.BusinessException;
import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.cart.dto.CartItemPublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartItemUpdatePublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartPublicRequest;
import com.jugger.springcommerce.modules.cart.dto.CartPublicResponse;
import com.jugger.springcommerce.modules.cart.enums.CartItemStatus;
import com.jugger.springcommerce.modules.cart.enums.CartStatus;
import com.jugger.springcommerce.modules.cart.mapper.CartPublicMapper;
import com.jugger.springcommerce.modules.cart.model.Cart;
import com.jugger.springcommerce.modules.cart.model.CartItem;
import com.jugger.springcommerce.modules.cart.repository.CartItemRepository;
import com.jugger.springcommerce.modules.cart.repository.CartRepository;
import com.jugger.springcommerce.modules.product.enums.ProductStatus;
import com.jugger.springcommerce.modules.product.model.Product;
import com.jugger.springcommerce.modules.product.repository.ProductRepository;
import com.jugger.springcommerce.modules.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private static final String DEFAULT_CURRENCY = "NRS";

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartPublicMapper cartPublicMapper;

    @Override
    @Transactional(readOnly = true)
    public CartPublicResponse getActiveCart(String sessionId) {
        validateSessionId(sessionId);
        Cart cart = cartRepository.findBySessionIdAndStatus(sessionId, CartStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active cart not found for session: " + sessionId));
        return cartPublicMapper.mapToPublicResponse(cart);
    }

    @Override
    @Transactional
    public CartPublicResponse createOrGetActiveCart(CartPublicRequest request) {
        if (request == null) {
            throw new BusinessException("Cart request is required");
        }

        validateSessionId(request.getSessionId());

        Cart cart = cartRepository.findBySessionIdAndStatus(request.getSessionId(), CartStatus.ACTIVE)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .sessionId(request.getSessionId().trim())
                                .currencyCode(normalizeCurrency(request.getCurrencyCode()))
                                .status(CartStatus.ACTIVE)
                                .build()
                ));

        return cartPublicMapper.mapToPublicResponse(cart);
    }

    @Override
    @Transactional
    public CartPublicResponse addItemToCart(CartItemPublicRequest request) {
        if (request == null) {
            throw new BusinessException("Cart item request is required");
        }

        validateSessionId(request.getSessionId());

        if (request.getProductId() == null) {
            throw new BusinessException("Product id is required");
        }

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BusinessException("Quantity must be greater than zero");
        }

        Product product = productRepository.findById(request.getProductId())
                .filter(found -> found.getStatus() == ProductStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active product not found with id: " + request.getProductId()));

        Cart cart = cartRepository.findBySessionIdAndStatus(request.getSessionId(), CartStatus.ACTIVE)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .sessionId(request.getSessionId().trim())
                                .currencyCode(normalizeCurrency(request.getCurrencyCode()))
                                .status(CartStatus.ACTIVE)
                                .build()
                ));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> createCartItem(cart, product, normalizeCurrency(request.getCurrencyCode())));

        cartItem.setStatus(CartItemStatus.ACTIVE);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setCurrencyCode(normalizeCurrency(request.getCurrencyCode()));
        cartItem.setProductNameSnapshot(product.getName());
        cartItem.setProductSlugSnapshot(product.getSlug());
        cartItem.setUnitPriceSnapshot(product.getPrice());
        cartItem.setCompareAtSnapshot(product.getCompareAt());
        cartItem.setLineSubtotalAmount(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        cartItem.setLineDiscountAmount(BigDecimal.ZERO);
        cartItem.setLineTotalAmount(cartItem.getLineSubtotalAmount());
        cartItemRepository.save(cartItem);

        cart.getItems().removeIf(item -> item.getId() != null && item.getId().equals(cartItem.getId()));
        cart.getItems().add(cartItem);
        recalculateCartTotals(cart);

        return cartPublicMapper.mapToPublicResponse(cartRepository.save(cart));
    }

    private CartItem createCartItem(Cart cart, Product product, String currencyCode) {
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .status(CartItemStatus.ACTIVE)
                .quantity(0)
                .currencyCode(currencyCode)
                .productNameSnapshot(product.getName())
                .productSlugSnapshot(product.getSlug())
                .unitPriceSnapshot(product.getPrice())
                .compareAtSnapshot(product.getCompareAt())
                .build();
    }

    private void recalculateCartTotals(Cart cart) {
        int itemCount = cart.getItems().stream()
                .filter(item -> item.getStatus() == CartItemStatus.ACTIVE)
                .mapToInt(CartItem::getQuantity)
                .sum();

        BigDecimal subtotal = cart.getItems().stream()
                .filter(item -> item.getStatus() == CartItemStatus.ACTIVE)
                .map(CartItem::getLineSubtotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discount = cart.getItems().stream()
                .filter(item -> item.getStatus() == CartItemStatus.ACTIVE)
                .map(CartItem::getLineDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setItemCount(itemCount);
        cart.setSubtotalAmount(subtotal);
        cart.setDiscountAmount(discount);
        cart.setTaxAmount(BigDecimal.ZERO);
        cart.setShippingAmount(BigDecimal.ZERO);
        cart.setGrandTotalAmount(subtotal.subtract(discount));
    }

    private void validateSessionId(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            throw new BusinessException("Session id is required");
        }
    }

    private String normalizeCurrency(String currencyCode) {
        if (currencyCode == null || currencyCode.isBlank()) {
            return DEFAULT_CURRENCY;
        }
        return currencyCode.trim().toUpperCase();
    }

    @Override
    @Transactional
    public CartPublicResponse updateCartItemQuantity(Long cartItemId, CartItemUpdatePublicRequest request) {
        if (request == null) {
            throw new BusinessException("Cart item update request is required");
        }

        if (cartItemId == null) {
            throw new BusinessException("Cart item id is required");
        }

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BusinessException("Quantity must be greater than zero");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        Cart cart = cartItem.getCart();
        if (cart == null || cart.getStatus() != CartStatus.ACTIVE) {
            throw new BusinessException("Cart item does not belong to an active cart");
        }

        Product product = cartItem.getProduct();
        if (product == null || product.getStatus() != ProductStatus.ACTIVE) {
            throw new BusinessException("Cart item product is no longer active");
        }

        cartItem.setStatus(CartItemStatus.ACTIVE);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setLineSubtotalAmount(cartItem.getUnitPriceSnapshot().multiply(BigDecimal.valueOf(request.getQuantity())));
        cartItem.setLineDiscountAmount(BigDecimal.ZERO);
        cartItem.setLineTotalAmount(cartItem.getLineSubtotalAmount());
        cartItemRepository.save(cartItem);

        cart.getItems().removeIf(item -> item.getId() != null && item.getId().equals(cartItem.getId()));
        cart.getItems().add(cartItem);
        recalculateCartTotals(cart);

        return cartPublicMapper.mapToPublicResponse(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartPublicResponse deleteCartItem(Long cartItemId) {
        if (cartItemId == null) {
            throw new BusinessException("Cart item id is required");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        Cart cart = cartItem.getCart();
        if (cart == null || cart.getStatus() != CartStatus.ACTIVE) {
            throw new BusinessException("Cart item does not belong to an active cart");
        }

        cart.getItems().removeIf(item -> item.getId() != null && item.getId().equals(cartItemId));
        recalculateCartTotals(cart);

        return cartPublicMapper.mapToPublicResponse(cartRepository.save(cart));
    }
}
