package com.jugger.springcommerce.modules.product.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageAdminRequest {
    private String url;
    private Boolean isPrimary;
    private Integer displayOrder;
}
