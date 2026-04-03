package com.jugger.springcommerce.modules.order.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import com.jugger.springcommerce.modules.order.enums.PaymentProvider;
import com.jugger.springcommerce.modules.order.enums.PaymentVerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "payment_submissions",
        indexes = {
                @Index(name = "idx_payment_submissions_order_id", columnList = "order_id"),
                @Index(name = "idx_payment_submissions_provider", columnList = "provider"),
                @Index(name = "idx_payment_submissions_verification_status", columnList = "verification_status")
        }
)
public class PaymentSubmission extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_submissions_seq_gen")
    @SequenceGenerator(name = "payment_submissions_seq_gen", sequenceName = "payment_submissions_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentProvider provider;

    @Builder.Default
    @Column(name = "expected_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal expectedAmount = BigDecimal.ZERO;

    @Column(name = "paid_amount", precision = 15, scale = 2)
    private BigDecimal paidAmount;

    @Column(name = "payer_mobile", length = 30)
    private String payerMobile;

    @Column(name = "transaction_reference", length = 120)
    private String transactionReference;

    @Column(name = "receipt_image_url", columnDefinition = "TEXT")
    private String receiptImageUrl;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 20)
    private PaymentVerificationStatus verificationStatus = PaymentVerificationStatus.PENDING;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    @Column(name = "verified_by", length = 100)
    private String verifiedBy;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;
}
