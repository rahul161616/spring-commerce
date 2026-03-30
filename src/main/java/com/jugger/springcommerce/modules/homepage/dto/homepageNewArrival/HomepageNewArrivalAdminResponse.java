package com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival;

import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.model.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HomepageNewArrivalAdminResponse {
    private Long id;
    private Integer limitCount;
    private Long categoryId;
    private String categoryName;
    private Long tagId;
    private String tagName;
    private Boolean onlyActive;
    private Boolean isActive;
}

