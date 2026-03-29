package com.jugger.springcommerce.modules.product.dto.admin;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductAdminRequest {

    private String name;

    private String description;

    @DecimalMin(value = "0.01", message = "price must be greater than 0")
    private BigDecimal price;

    @PositiveOrZero(message = "stockQuantity must be 0 or greater")
    private Integer stockQuantity;

    @Positive(message = "categoryId must be greater than 0")
    private Long categoryId;

    private List<@Positive(message = "tagId must be greater than 0") Long> tagIds;

    private Boolean isFeatured;

    private List<ProductImageAdminRequest> images;
}
