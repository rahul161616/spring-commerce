package com.jugger.springcommerce.modules.homepage.dto.trendingProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HomepageTrendingProductAdminResponse {
    private Long id;
    private Long productId;
    private String label;
    private Boolean isActive;
    private Integer displayOrder;
}
