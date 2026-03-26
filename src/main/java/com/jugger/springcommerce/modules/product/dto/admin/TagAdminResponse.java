package com.jugger.springcommerce.modules.product.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TagAdminResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Boolean isActive;
}
