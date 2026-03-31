package com.jugger.springcommerce.modules.product.dto.admin;

import com.jugger.springcommerce.modules.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UpdateStatusResponseForAdmin {
    private String message;
    private ProductStatus status;
}
