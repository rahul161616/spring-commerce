package com.jugger.springcommerce.modules.order.dto;

import com.jugger.springcommerce.modules.order.enums.PaymentProvider;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSubmissionPublicRequest {
    private PaymentProvider provider;
    private BigDecimal paidAmount;
    private String payerMobile;
    private String transactionReference;
    private String receiptImageUrl;
    private String remarks;
}
