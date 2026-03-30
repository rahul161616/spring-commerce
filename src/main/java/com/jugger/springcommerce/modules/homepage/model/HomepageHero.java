package com.jugger.springcommerce.modules.homepage.model;

import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "homepage_hero")
public class HomepageHero {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="homepage_hero_seq_gen")
    @SequenceGenerator(name="homepage_hero_seq_gen",sequenceName = "homepage_hero_id_seq",allocationSize = 1)
    private Long id;

    private String eyebrow; //
    private String title;
    @Column(name="supporting_text")
    private String supportingText;
    @Column(name="image_url")
    private String imageUrl;
    @Column(name="cta_label")
    private String ctaLabel;
    @Column(name="cta_href")
    private String ctaUrl; //href
    @Builder.Default
    @Column(name="display_order")
    private Integer displayOrder=1;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="updated_by")
    private String updatedBy;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive=true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_category_id")
    private Category category;

}
