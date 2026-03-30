package com.jugger.springcommerce.modules.homepage.model;

import com.jugger.springcommerce.modules.product.model.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "homepage_featured_categories")
public class HomepageFeaturedCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "homepage_featured_categories_seq_gen")
    @SequenceGenerator(name = "homepage_featured_categories_seq_gen", sequenceName = "homepage_featured_categories_id_seq", allocationSize = 1)
    private Long id;
    private String caption;
    private String imageUrl;
    private String emphasis;
    private Boolean isActive = true;
    private Integer displayOrder = 1;
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
