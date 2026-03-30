package com.jugger.springcommerce.modules.homepage.dto.featuredCategory;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomepageFeaturedCategoryPublicResponse {
    private Long id;
    private String name;
    private String caption;
    private String href;
    private String imageUrl;
    private String emphasis;
}
