package com.jugger.springcommerce.modules.homepage.dto.featuredCategory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomepageFeaturedCategoryAdminResponse {
    private Long id;
    private String caption;
    private String imageUrl;
    private String emphasis;
    private Boolean isActive;
    private Integer displayOrder;
    private Long categoryId;
    private String categoryName;
}
