package com.jugger.springcommerce.modules.product.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "product_images",
        indexes = {
                @Index(name = "idx_product_images_product_id", columnList = "product_id"),
                @Index(name = "idx_product_images_is_primary", columnList = "is_primary")
        }
)
public class ProductImage extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="product_images_seq_gen")
    @SequenceGenerator(name="product_images_seq_gen",sequenceName = "product_images_id_seq",allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;
    }
