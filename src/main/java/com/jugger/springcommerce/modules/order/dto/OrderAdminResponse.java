package com.jugger.springcommerce.modules.order.dto;

import com.jugger.springcommerce.modules.order.enums.OrderStatus;
import com.jugger.springcommerce.modules.order.enums.PaymentProvider;
import com.jugger.springcommerce.modules.order.enums.PaymentVerificationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderAdminResponse {
    private Long id;
    private String orderCode;
    private String sessionId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String currencyCode;
    private BigDecimal subtotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal shippingAmount;
    private BigDecimal grandTotalAmount;
    private OrderStatus status;
    private String notes;
    private Integer itemCount;
    private Instant createdAt;
    private Long paymentSubmissionId;
    private PaymentProvider paymentProvider;
    private PaymentVerificationStatus paymentVerificationStatus;
    private BigDecimal expectedAmount;
    private BigDecimal paidAmount;
    private String payerMobile;
    private String transactionReference;
    private String receiptImageUrl;
    private String paymentRemarks;
    private Instant submittedAt;
}
