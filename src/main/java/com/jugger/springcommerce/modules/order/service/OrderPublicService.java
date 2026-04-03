package com.jugger.springcommerce.modules.order.service;

import com.jugger.springcommerce.modules.order.dto.OrderPublicRequest;
import com.jugger.springcommerce.modules.order.dto.OrderPublicResponse;
import com.jugger.springcommerce.modules.order.dto.PaymentSubmissionPublicRequest;
import com.jugger.springcommerce.modules.order.dto.PaymentSubmissionPublicResponse;

public interface OrderPublicService {
    OrderPublicResponse placeOrder(OrderPublicRequest orderPublicRequest);
    PaymentSubmissionPublicResponse submitPayment(String orderCode, PaymentSubmissionPublicRequest request);
    OrderPublicResponse getOrderByCode(String orderCode);
}
