package com.jugger.springcommerce.modules.order.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVerifyAdminResponse {
    String message;
    String status;
}
