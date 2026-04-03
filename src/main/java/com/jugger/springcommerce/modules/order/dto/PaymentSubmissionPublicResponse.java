package com.jugger.springcommerce.modules.order.dto;

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
public class PaymentSubmissionPublicResponse {
    private Long id;
    private String orderCode;
    private PaymentProvider provider;
    private BigDecimal expectedAmount;
    private BigDecimal paidAmount;
    private String payerMobile;
    private String transactionReference;
    private String receiptImageUrl;
    private String remarks;
    private PaymentVerificationStatus verificationStatus;
    private Instant submittedAt;
}
