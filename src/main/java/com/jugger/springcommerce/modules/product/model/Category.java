package com.jugger.springcommerce.modules.product.model;

import com.jugger.springcommerce.common.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_categories_name", columnList = "name"),
                @Index(name = "idx_categories_slug", columnList = "slug"),
                @Index(name = "idx_categories_parent_id", columnList = "parent_id")
        }
)
public class Category extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="categories_seq_gen")
    @SequenceGenerator(name="categories_seq_gen",sequenceName = "categories_id_seq",allocationSize = 1)
    private Long id;
    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Category> children = new HashSet<>();
}

