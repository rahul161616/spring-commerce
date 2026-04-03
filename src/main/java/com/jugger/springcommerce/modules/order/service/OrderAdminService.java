package com.jugger.springcommerce.modules.order.service;

import com.jugger.springcommerce.modules.order.dto.OrderAdminResponse;
import com.jugger.springcommerce.modules.order.dto.OrderVerifyAdminRequest;
import com.jugger.springcommerce.modules.order.dto.OrderVerifyAdminResponse;
//import com.jugger.springcommerce.modules.order.dto.OrderVerifyAdminResponse;

import java.util.List;

public interface OrderAdminService {

    List<OrderAdminResponse> getOrderDetails();
    OrderAdminResponse getOrderDetailsById(Long id);
    OrderVerifyAdminResponse verifyOrder(OrderVerifyAdminRequest request);
    OrderVerifyAdminResponse cancelOrder(OrderVerifyAdminRequest request);
    OrderVerifyAdminResponse processOrder(OrderVerifyAdminRequest request);

}
