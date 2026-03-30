package com.jugger.springcommerce.modules.homepage.dto.hero;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomepageHeroAdminRequest {
    private Long id;
    private String eyebrow;
    private String title;
    private String supportingText;
    private String imageUrl;
    private String ctaLabel;
    private String ctaUrl;
    private Integer displayOrder;
    private Boolean isActive;
    private Long linkProductId;
    private Long linkCategoryId;
    private Boolean unlinkProduct;
    private Boolean unlinkCategory;
}
