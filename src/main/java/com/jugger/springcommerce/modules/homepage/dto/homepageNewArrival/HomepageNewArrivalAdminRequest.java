package com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival;

import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HomepageNewArrivalAdminRequest {
    private Integer limitCount;
    private Long categoryId;
    private Long tagId;
    private Boolean onlyActive;
    private Boolean isActive;
}
