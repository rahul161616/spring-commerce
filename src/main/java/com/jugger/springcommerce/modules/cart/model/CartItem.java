package com.jugger.springcommerce.modules.cart.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import com.jugger.springcommerce.modules.cart.enums.CartItemStatus;
import com.jugger.springcommerce.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "cart_items",
        indexes = {
                @Index(name = "idx_cart_items_cart_id", columnList = "cart_id"),
                @Index(name = "idx_cart_items_product_id", columnList = "product_id"),
                @Index(name = "idx_cart_items_status", columnList = "status")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cart_items_cart_product", columnNames = {"cart_id", "product_id"})
        }
)
public class CartItem extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_items_seq_gen")
    @SequenceGenerator(name = "cart_items_seq_gen", sequenceName = "cart_items_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CartItemStatus status = CartItemStatus.ACTIVE;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "product_name_snapshot", nullable = false, length = 200)
    private String productNameSnapshot;

    @Column(name = "product_slug_snapshot", nullable = false, length = 220)
    private String productSlugSnapshot;

    @Column(name = "sku_snapshot", length = 120)
    private String skuSnapshot;

    @Builder.Default
    @Column(name = "unit_price_snapshot", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPriceSnapshot = BigDecimal.ZERO;

    @Column(name = "compare_at_snapshot", precision = 15, scale = 2)
    private BigDecimal compareAtSnapshot;

    @Builder.Default
    @Column(name = "line_subtotal_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal lineSubtotalAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "line_discount_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal lineDiscountAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "line_total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal lineTotalAmount = BigDecimal.ZERO;
}
