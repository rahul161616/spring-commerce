package com.jugger.springcommerce.modules.homepage.dto.trendingProduct;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomepageTrendingProductPublicResponse {
    private Long id;
    private String family;
    private String title;
    private String price;
    private String compareAt;
    private String image;
    private String href;
}
