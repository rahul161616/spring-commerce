package com.jugger.springcommerce.modules.product.dto.admin;

import com.jugger.springcommerce.modules.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductAdminResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private BigDecimal price;
    private BigDecimal compareAt;
    private Integer stockQuantity;
    private String categoryName;
    private Boolean isFeatured;
    private List<String> tags;
    private List<ProductImageAdminResponse> images;
    private ProductStatus status;

}
