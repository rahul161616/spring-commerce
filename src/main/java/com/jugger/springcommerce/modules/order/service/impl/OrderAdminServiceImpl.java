package com.jugger.springcommerce.modules.order.service.impl;

import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.order.dto.OrderAdminResponse;
import com.jugger.springcommerce.modules.order.dto.OrderVerifyAdminRequest;
import com.jugger.springcommerce.modules.order.dto.OrderVerifyAdminResponse;
import com.jugger.springcommerce.modules.order.enums.OrderStatus;
import com.jugger.springcommerce.modules.order.enums.PaymentProvider;
import com.jugger.springcommerce.modules.order.enums.PaymentVerificationStatus;
import com.jugger.springcommerce.modules.order.mapper.OrderAdminMapper;
import com.jugger.springcommerce.modules.order.model.Order;
import com.jugger.springcommerce.modules.order.model.PaymentSubmission;
import com.jugger.springcommerce.modules.order.repository.OrderRepository;
import com.jugger.springcommerce.modules.order.repository.PaymentSubmissionRepository;
import com.jugger.springcommerce.modules.order.service.OrderAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderAdminServiceImpl implements OrderAdminService {
    private final JdbcTemplate jdbcTemplate;
    private final OrderAdminMapper orderAdminMapper;
    private final OrderRepository orderRepository;
    private final PaymentSubmissionRepository paymentSubmissionRepository;

    @Override
    public OrderAdminResponse getOrderDetailsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Order id cannot be null");
        }
        String sql = """
                SELECT o.id,
                       o.order_code,
                       o.session_id,
                       COALESCE(o.customer_name, u.full_name) AS customer_name,
                       COALESCE(o.customer_phone, u.phone) AS customer_phone,
                       COALESCE(o.customer_email, u.email) AS customer_email,
                       o.currency_code,
                       o.subtotal_amount,
                       o.discount_amount,
                       o.tax_amount,
                       o.shipping_amount,
                       o.grand_total_amount,
                       o.status,
                       o.notes,
                       o.created_at,
                       COALESCE(SUM(oi.quantity), 0) AS item_count,
                       ps.id AS payment_submission_id,
                       ps.provider AS payment_provider,
                       ps.verification_status AS payment_verification_status,
                       ps.expected_amount,
                       ps.paid_amount,
                       ps.payer_mobile,
                       ps.transaction_reference,
                       ps.receipt_image_url,
                       ps.remarks AS payment_remarks,
                       ps.submitted_at
                FROM orders o
                LEFT JOIN users u ON u.id = o.user_id
                LEFT JOIN order_items oi ON oi.order_id = o.id
                LEFT JOIN LATERAL (
                    SELECT p.id,
                           p.provider,
                           p.verification_status,
                           p.expected_amount,
                           p.paid_amount,
                           p.payer_mobile,
                           p.transaction_reference,
                           p.receipt_image_url,
                           p.remarks,
                           p.submitted_at
                    FROM payment_submissions p
                    WHERE p.order_id = o.id
                    ORDER BY p.id DESC
                    LIMIT 1
                ) ps ON TRUE
                WHERE o.id = ?
                GROUP BY o.id,
                         o.order_code,
                         o.session_id,
                         o.customer_name,
                         o.customer_phone,
                         o.customer_email,
                         u.full_name,
                         u.phone,
                         u.email,
                         o.currency_code,
                         o.subtotal_amount,
                         o.discount_amount,
                         o.tax_amount,
                         o.shipping_amount,
                         o.grand_total_amount,
                         o.status,
                         o.notes,
                         o.created_at,
                         ps.id,
                         ps.provider,
                         ps.verification_status,
                         ps.expected_amount,
                         ps.paid_amount,
                         ps.payer_mobile,
                         ps.transaction_reference,
                         ps.receipt_image_url,
                         ps.remarks,
                         ps.submitted_at
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> orderAdminMapper.mapToAdminResponse(rs),
                    id
            );
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
    }

    @Override
    public List<OrderAdminResponse> getOrderDetails() {
        String sql = """
                SELECT o.id,
                       o.order_code,
                       o.session_id,
                       COALESCE(o.customer_name, u.full_name) AS customer_name,
                       COALESCE(o.customer_phone, u.phone) AS customer_phone,
                       COALESCE(o.customer_email, u.email) AS customer_email,
                       o.currency_code,
                       o.subtotal_amount,
                       o.discount_amount,
                       o.tax_amount,
                       o.shipping_amount,
                       o.grand_total_amount,
                       o.status,
                       o.notes,
                       o.created_at,
                       COALESCE(SUM(oi.quantity), 0) AS item_count,
                       ps.id AS payment_submission_id,
                       ps.provider AS payment_provider,
                       ps.verification_status AS payment_verification_status,
                       ps.expected_amount,
                       ps.paid_amount,
                       ps.payer_mobile,
                       ps.transaction_reference,
                       ps.receipt_image_url,
                       ps.remarks AS payment_remarks,
                       ps.submitted_at
                FROM orders o
                LEFT JOIN users u ON u.id = o.user_id
                LEFT JOIN order_items oi ON oi.order_id = o.id
                LEFT JOIN LATERAL (
                    SELECT p.id,
                           p.provider,
                           p.verification_status,
                           p.expected_amount,
                           p.paid_amount,
                           p.payer_mobile,
                           p.transaction_reference,
                           p.receipt_image_url,
                           p.remarks,
                           p.submitted_at
                    FROM payment_submissions p
                    WHERE p.order_id = o.id
                    ORDER BY p.id DESC
                    LIMIT 1
                ) ps ON TRUE
                GROUP BY o.id,
                         o.order_code,
                         o.session_id,
                         o.customer_name,
                         o.customer_phone,
                         o.customer_email,
                         u.full_name,
                         u.phone,
                         u.email,
                         o.currency_code,
                         o.subtotal_amount,
                         o.discount_amount,
                         o.tax_amount,
                         o.shipping_amount,
                         o.grand_total_amount,
                         o.status,
                         o.notes,
                         o.created_at,
                         ps.id,
                         ps.provider,
                         ps.verification_status,
                         ps.expected_amount,
                         ps.paid_amount,
                         ps.payer_mobile,
                         ps.transaction_reference,
                         ps.receipt_image_url,
                         ps.remarks,
                         ps.submitted_at
                ORDER BY o.id DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> orderAdminMapper.mapToAdminResponse(rs));
    }

    @Override
    @Transactional
    public OrderVerifyAdminResponse verifyOrder(OrderVerifyAdminRequest request) {
        return updateOrderState(request, "Order verified successfully");
    }

    @Override
    @Transactional
    public OrderVerifyAdminResponse cancelOrder(OrderVerifyAdminRequest request) {
        return updateOrderState(request, "Order cancelled successfully");
    }

    @Override
    @Transactional
    public OrderVerifyAdminResponse processOrder(OrderVerifyAdminRequest request) {
        return updateOrderState(request, "Order processed successfully");
    }

    private OrderVerifyAdminResponse updateOrderState(OrderVerifyAdminRequest request, String successMessage) {
        if (request == null) {
            throw new IllegalArgumentException("Verification request cannot be null");
        }
        if (request.getOrderId() == null) {
            throw new IllegalArgumentException("Order id cannot be null");
        }
        if (request.getOrderStatus() == null || request.getOrderStatus().isBlank()) {
            throw new IllegalArgumentException("Order status cannot be null");
        }
        if (request.getPaymentStatus() == null || request.getPaymentStatus().isBlank()) {
            throw new IllegalArgumentException("Payment status cannot be null");
        }
        if (request.getPaymentMethod() == null || request.getPaymentMethod().isBlank()) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        OrderStatus targetOrderStatus = OrderStatus.valueOf(request.getOrderStatus().trim().toUpperCase());
        PaymentVerificationStatus targetPaymentStatus = PaymentVerificationStatus.valueOf(request.getPaymentStatus().trim().toUpperCase());
        PaymentProvider targetPaymentProvider = PaymentProvider.valueOf(request.getPaymentMethod().trim().toUpperCase());

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.getOrderId()));

        PaymentSubmission paymentSubmission = paymentSubmissionRepository.findByOrderId(order.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment submission not found for order id: " + order.getId()));

        if (order.getStatus() == targetOrderStatus
                && paymentSubmission.getVerificationStatus() == targetPaymentStatus
                && paymentSubmission.getProvider() == targetPaymentProvider) {
            return OrderVerifyAdminResponse.builder()
                    .message("Order is already verified with the requested state")
                    .status(order.getStatus().name())
                    .build();
        }

        order.setStatus(targetOrderStatus);
        paymentSubmission.setVerificationStatus(targetPaymentStatus);
        paymentSubmission.setProvider(targetPaymentProvider);
        paymentSubmission.setVerifiedAt(Instant.now());

        orderRepository.save(order);
        paymentSubmissionRepository.save(paymentSubmission);

        return OrderVerifyAdminResponse.builder()
                .message(successMessage)
                .status(order.getStatus().name())
                .build();
    }
}
