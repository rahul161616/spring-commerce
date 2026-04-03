package com.jugger.springcommerce.modules.order.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
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
        name = "order_items",
        indexes = {
                @Index(name = "idx_order_items_order_id", columnList = "order_id"),
                @Index(name = "idx_order_items_product_id", columnList = "product_id")
        }
)
public class OrderItem extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_seq_gen")
    @SequenceGenerator(name = "order_items_seq_gen", sequenceName = "order_items_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_name_snapshot", nullable = false, length = 200)
    private String productNameSnapshot;

    @Column(name = "product_slug_snapshot", nullable = false, length = 220)
    private String productSlugSnapshot;

    @Column(name = "sku_snapshot", length = 120)
    private String skuSnapshot;

    @Column(nullable = false)
    private Integer quantity;

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
