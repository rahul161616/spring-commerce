package com.jugger.springcommerce.modules.product.dto.admin;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryAdminRequest {
    private String name;
    private Long parentId;
    private String description;
    private Boolean isActive;
}
