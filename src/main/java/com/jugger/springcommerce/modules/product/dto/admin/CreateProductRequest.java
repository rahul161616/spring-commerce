package com.jugger.springcommerce.modules.product.dto.admin;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {
    @NotBlank(message = "name is required")
    @Size(max = 200, message = "name must be at most 200 characters")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.01", message = "price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "stockQuantity is required")
    @PositiveOrZero(message = "stockQuantity must be 0 or greater")
    private Integer stockQuantity;

    @NotNull(message = "categoryId is required")
    @Positive(message = "categoryId must be greater than 0")
    private Long categoryId;

    private List<@NotNull(message = "tagId cannot be null") @Positive(message = "tagId must be greater than 0") Long> tagIds;

    @NotNull(message = "isFeatured is required")
    private Boolean isFeatured;

}
