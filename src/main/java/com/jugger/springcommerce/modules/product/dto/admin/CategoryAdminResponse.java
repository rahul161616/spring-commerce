package com.jugger.springcommerce.modules.product.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryAdminResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Boolean isActive;

    private Long parentId;
    private String parentName;
}
