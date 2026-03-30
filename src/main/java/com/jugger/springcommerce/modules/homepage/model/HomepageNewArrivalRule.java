package com.jugger.springcommerce.modules.homepage.model;

import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.model.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "homepage_new_arrivals_rule")
public class HomepageNewArrivalRule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "homepage_new_arrivals_rule_seq_gen")
    @SequenceGenerator(name = "homepage_new_arrivals_rule_seq_gen",sequenceName = "homepage_new_arrivals_rule_id_seq",allocationSize = 1)
    private Long id;
    private Integer limitCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
    private Boolean onlyActive;
    private Boolean isActive;
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
}
