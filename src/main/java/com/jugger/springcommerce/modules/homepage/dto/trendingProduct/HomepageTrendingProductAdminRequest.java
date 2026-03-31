package com.jugger.springcommerce.modules.homepage.dto.trendingProduct;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HomepageTrendingProductAdminRequest {
    private Long productId;
    private String label;
    private Boolean isActive;
    private Integer displayOrder;
}
