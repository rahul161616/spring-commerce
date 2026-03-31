package com.jugger.springcommerce.modules.product.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageAdminResponse {
    private Long id;
    private String imageUrl;
    private Boolean isPrimary;
    private Integer displayOrder;
}