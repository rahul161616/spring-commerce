package com.jugger.springcommerce.modules.product.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import com.jugger.springcommerce.modules.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_products_name", columnList = "name"),
                @Index(name = "idx_products_category_id", columnList = "category_id"),
                @Index(name = "idx_products_status", columnList = "status"),
                @Index(name = "idx_products_is_featured", columnList = "is_featured")
        }
)
public class Product extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="products_seq_gen")
    @SequenceGenerator(name="products_seq_gen",sequenceName = "products_id_seq",allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, unique = true, length = 220)
    private String slug;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(precision = 15, scale = 2)
    private BigDecimal compareAt;

    @Builder.Default
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProductStatus status = ProductStatus.DRAFT;

    @Builder.Default
    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductTag> productTags = new HashSet<>();
}

