package com.jugger.springcommerce.modules.product.dto.admin;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagAdminRequest {
    private String name;
    private String description;
    private Boolean isActive;
}
