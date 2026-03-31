package com.jugger.springcommerce.modules.homepage.model;

import com.jugger.springcommerce.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "homepage_trending_products")
public class HomepageTrendingProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "homepage_trending_products_seq_gen")
    @SequenceGenerator(name = "homepage_trending_products_seq_gen", sequenceName = "homepage_trending_products_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String label;
    private Boolean isActive = true;
    private Integer displayOrder = 1;
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
}
