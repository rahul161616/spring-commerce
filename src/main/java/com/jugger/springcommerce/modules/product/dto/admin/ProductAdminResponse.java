package com.jugger.springcommerce.modules.product.dto.admin;

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
    private BigDecimal price;
    private Integer stockQuantity;
    private String categoryName;
    private List<String> tags;
    private List<ProductImageAdminResponse> images;

}
