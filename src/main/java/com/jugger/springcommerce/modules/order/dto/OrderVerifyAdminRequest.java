package com.jugger.springcommerce.modules.order.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderVerifyAdminRequest {
    Long orderId;
    String orderStatus;
    String paymentStatus;
    String paymentMethod;
}
