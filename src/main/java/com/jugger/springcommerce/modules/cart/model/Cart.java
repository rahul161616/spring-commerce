package com.jugger.springcommerce.modules.cart.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import com.jugger.springcommerce.modules.cart.enums.CartStatus;
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
        name = "carts",
        indexes = {
                @Index(name = "idx_carts_user_id", columnList = "user_id"),
                @Index(name = "idx_carts_session_id", columnList = "session_id"),
                @Index(name = "idx_carts_status", columnList = "status"),
                @Index(name = "idx_carts_expires_at", columnList = "expires_at")
        }
)
public class Cart extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carts_seq_gen")
    @SequenceGenerator(name = "carts_seq_gen", sequenceName = "carts_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CartStatus status = CartStatus.ACTIVE;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Builder.Default
    @Column(name = "item_count", nullable = false)
    private Integer itemCount = 0;

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

    @Version
    @Column(nullable = false)
    @Builder.Default
    private Long version = 0L;

    @Column(name = "expires_at")
    private java.time.Instant expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merged_into_cart_id")
    private Cart mergedIntoCart;

    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();
}
