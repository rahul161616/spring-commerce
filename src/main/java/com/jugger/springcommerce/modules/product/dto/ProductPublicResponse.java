package com.jugger.springcommerce.modules.product.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class ProductPublicResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal compareAt;
    private String imageUrl;
    private String categoryName;
    private String tagNames;
    private String tagIds;
    private String slug;
    private String shortDescription;
    private Integer stockQuantity;
}
