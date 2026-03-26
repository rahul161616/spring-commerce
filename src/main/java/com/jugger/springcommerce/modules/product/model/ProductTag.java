package com.jugger.springcommerce.modules.product.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@Table(
        name = "product_tags",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_tags_product_tag", columnNames = {"product_id", "tag_id"})
        },
        indexes = {
                @Index(name = "idx_product_tags_product_id", columnList = "product_id"),
                @Index(name = "idx_product_tags_tag_id", columnList = "tag_id")
        }
)
public class ProductTag {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="product_tags_seq_gen")
    @SequenceGenerator(name="product_tags_seq_gen",sequenceName = "product_tags_seq_id",allocationSize = 1)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    private String createdBy;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (createdBy == null) {
            createdBy = "SYSTEM";
        }
    }
}
