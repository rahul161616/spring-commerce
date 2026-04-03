package com.jugger.springcommerce.modules.order.service.impl;

import com.jugger.springcommerce.common.exception.BusinessException;
import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.cart.enums.CartStatus;
import com.jugger.springcommerce.modules.cart.model.Cart;
import com.jugger.springcommerce.modules.cart.model.CartItem;
import com.jugger.springcommerce.modules.cart.repository.CartRepository;
import com.jugger.springcommerce.modules.order.dto.OrderPublicRequest;
import com.jugger.springcommerce.modules.order.dto.OrderPublicResponse;
import com.jugger.springcommerce.modules.order.dto.PaymentSubmissionPublicRequest;
import com.jugger.springcommerce.modules.order.dto.PaymentSubmissionPublicResponse;
import com.jugger.springcommerce.modules.order.enums.OrderStatus;
import com.jugger.springcommerce.modules.order.enums.PaymentVerificationStatus;
import com.jugger.springcommerce.modules.order.mapper.OrderPublicMapper;
import com.jugger.springcommerce.modules.order.model.Order;
import com.jugger.springcommerce.modules.order.model.OrderItem;
import com.jugger.springcommerce.modules.order.model.PaymentSubmission;
import com.jugger.springcommerce.modules.order.repository.OrderRepository;
import com.jugger.springcommerce.modules.order.repository.PaymentSubmissionRepository;
import com.jugger.springcommerce.modules.order.service.OrderPublicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderPublicServiceImpl implements OrderPublicService {
    private final OrderRepository orderRepository;
    private final PaymentSubmissionRepository paymentSubmissionRepository;
    private final CartRepository cartRepository;
    private final OrderPublicMapper orderPublicMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public OrderPublicResponse placeOrder(OrderPublicRequest orderPublicRequest) {
        if (orderPublicRequest == null) {
            throw new BusinessException("Order request is required");
        }

        if (orderPublicRequest.getSessionId() == null || orderPublicRequest.getSessionId().isBlank()) {
            throw new BusinessException("Session id is required");
        }

        Cart cart = cartRepository.findBySessionIdAndStatus(orderPublicRequest.getSessionId().trim(), CartStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active cart not found for session: " + orderPublicRequest.getSessionId()));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new BusinessException("Cannot place order with an empty cart");
        }

        Order existingOrder = orderRepository.findTopBySessionIdAndStatusInOrderByIdDesc(
                        cart.getSessionId(),
                        List.of(OrderStatus.PENDING_PAYMENT, OrderStatus.PAYMENT_SUBMITTED)
                )
                .orElse(null);

        if (existingOrder != null) {
            return orderPublicMapper.mapToPublicResponse(existingOrder);
        }

        Order order = Order.builder()
                .orderCode(generateOrderCode())
                .user(cart.getUser())
                .sessionId(cart.getSessionId())
                .customerName(orderPublicRequest.getCustomerName())
                .customerEmail(orderPublicRequest.getCustomerEmail())
                .customerPhone(orderPublicRequest.getCustomerPhone())
                .currencyCode(cart.getCurrencyCode())
                .subtotalAmount(cart.getSubtotalAmount())
                .discountAmount(cart.getDiscountAmount())
                .taxAmount(cart.getTaxAmount())
                .shippingAmount(cart.getShippingAmount())
                .grandTotalAmount(cart.getGrandTotalAmount())
                .status(orderPublicRequest.getStatus() != null ? orderPublicRequest.getStatus() : OrderStatus.PENDING_PAYMENT)
                .notes(orderPublicRequest.getNotes())
                .build();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .productNameSnapshot(cartItem.getProductNameSnapshot())
                    .productSlugSnapshot(cartItem.getProductSlugSnapshot())
                    .skuSnapshot(cartItem.getSkuSnapshot())
                    .quantity(cartItem.getQuantity())
                    .unitPriceSnapshot(cartItem.getUnitPriceSnapshot())
                    .compareAtSnapshot(cartItem.getCompareAtSnapshot())
                    .lineSubtotalAmount(cartItem.getLineSubtotalAmount())
                    .lineDiscountAmount(cartItem.getLineDiscountAmount())
                    .lineTotalAmount(cartItem.getLineTotalAmount())
                    .build();
            order.getItems().add(orderItem);
        }
        log.info("Order placed successfully: {}", order);
        Order savedOrder = orderRepository.save(order);
        log.info("Order placed successfully: {}", savedOrder);
        return orderPublicMapper.mapToPublicResponse(savedOrder);
    }

    @Override
    @Transactional
    public PaymentSubmissionPublicResponse submitPayment(String orderCode, PaymentSubmissionPublicRequest request) {
        if (orderCode == null || orderCode.isBlank()) {
            throw new BusinessException("Order code is required");
        }

        if (request == null) {
            throw new BusinessException("Payment submission request is required");
        }

        if (request.getProvider() == null) {
            throw new BusinessException("Payment provider is required");
        }

        Order order = orderRepository.findByOrderCode(orderCode.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with code: " + orderCode));

        if (paymentSubmissionRepository.existsByOrderId(order.getId())) {
            throw new BusinessException("Payment has already been submitted for this order");
        }

        if (order.getStatus() == OrderStatus.PAYMENT_SUBMITTED || order.getStatus() == OrderStatus.PAYMENT_VERIFIED) {
            throw new BusinessException("Order is already in payment review");
        }

        PaymentSubmission paymentSubmission = PaymentSubmission.builder()
                .order(order)
                .provider(request.getProvider())
                .expectedAmount(order.getGrandTotalAmount())
                .paidAmount(request.getPaidAmount())
                .payerMobile(request.getPayerMobile())
                .transactionReference(request.getTransactionReference())
                .receiptImageUrl(request.getReceiptImageUrl())
                .remarks(request.getRemarks())
                .verificationStatus(PaymentVerificationStatus.PENDING)
                .submittedAt(java.time.Instant.now())
                .build();

        PaymentSubmission savedSubmission = paymentSubmissionRepository.save(paymentSubmission);
        order.setStatus(OrderStatus.PAYMENT_SUBMITTED);
        orderRepository.save(order);
        cartRepository.findBySessionIdAndStatus(order.getSessionId(), CartStatus.ACTIVE).ifPresent(cart -> {
            cart.setStatus(CartStatus.CONVERTED);
            cartRepository.save(cart);
        });

        return PaymentSubmissionPublicResponse.builder()
                .id(savedSubmission.getId())
                .orderCode(order.getOrderCode())
                .provider(savedSubmission.getProvider())
                .expectedAmount(savedSubmission.getExpectedAmount())
                .paidAmount(savedSubmission.getPaidAmount())
                .payerMobile(savedSubmission.getPayerMobile())
                .transactionReference(savedSubmission.getTransactionReference())
                .receiptImageUrl(savedSubmission.getReceiptImageUrl())
                .remarks(savedSubmission.getRemarks())
                .verificationStatus(savedSubmission.getVerificationStatus())
                .submittedAt(savedSubmission.getSubmittedAt())
                .build();
    }

    private String generateOrderCode() {
        return "SHDS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    public OrderPublicResponse getOrderByCode(String orderCode){

        String sql = """
                SELECT o.id,
                o.order_code,
                o.user_id,
                o.session_id,
                o.customer_name,
                o.customer_email,
                o.customer_phone,
                o.currency_code,
                o.subtotal_amount,
                o.discount_amount,
                o.tax_amount,
                o.shipping_amount,
                o.grand_total_amount,
                o.status,
                o.notes
                FROM orders o
                WHERE o.order_code = ?
                ORDER BY o.id DESC
                LIMIT 1 
                """;
        return jdbcTemplate.queryForObject(sql,(rs,rowNum)->orderPublicMapper.mapOrderByCodeToPublicResponse(rs),orderCode);
    }
}
