package com.jugger.springcommerce.modules.homepage.dto.hero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HomepageHeroPublicResponse {
    private Long id;
    private String eyebrow;
    private String title;
    private String ctaLabel;
    private String ctaUrl;
    private String imageUrl;
    private Long linkProductId;
    private Long linkCategoryId;
}
