package com.jugger.springcommerce.modules.product.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        name = "tags",
        indexes = {
                @Index(name = "idx_tags_name", columnList = "name"),
                @Index(name = "idx_tags_slug", columnList = "slug"),
                @Index(name = "idx_tags_is_active", columnList = "is_active")
        }
)
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="tags_seq_gen")
    @SequenceGenerator(name="tags_seq_gen",sequenceName = "tags_seq_id",allocationSize = 1)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 120)
    private String slug;

    @Column(length = 255)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductTag> productTags = new HashSet<>();
}
