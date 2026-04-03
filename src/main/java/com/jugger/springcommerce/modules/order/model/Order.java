package com.jugger.springcommerce.modules.order.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import com.jugger.springcommerce.modules.order.enums.OrderStatus;
import com.jugger.springcommerce.modules.user.model.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "orders",
        indexes = {
                @Index(name = "idx_orders_order_code", columnList = "order_code"),
                @Index(name = "idx_orders_user_id", columnList = "user_id"),
                @Index(name = "idx_orders_session_id", columnList = "session_id"),
                @Index(name = "idx_orders_status", columnList = "status")
        }
)
public class Order extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq_gen")
    @SequenceGenerator(name = "orders_seq_gen", sequenceName = "orders_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "order_code", nullable = false, unique = true, length = 40)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Column(name = "customer_name", length = 150)
    private String customerName;

    @Column(name = "customer_phone", length = 30)
    private String customerPhone;

    @Column(name = "customer_email", length = 180)
    private String customerEmail;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Builder.Default
    @Column(name = "subtotal_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotalAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "discount_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "tax_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "shipping_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal shippingAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "grand_total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal grandTotalAmount = BigDecimal.ZERO;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private OrderStatus status = OrderStatus.PENDING_PAYMENT;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaymentSubmission> paymentSubmissions = new HashSet<>();
}
