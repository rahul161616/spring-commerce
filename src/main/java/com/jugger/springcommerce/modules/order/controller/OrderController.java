package com.jugger.springcommerce.modules.order.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.order.dto.OrderPublicRequest;
import com.jugger.springcommerce.modules.order.dto.OrderPublicResponse;
import com.jugger.springcommerce.modules.order.dto.PaymentSubmissionPublicRequest;
import com.jugger.springcommerce.modules.order.dto.PaymentSubmissionPublicResponse;
import com.jugger.springcommerce.modules.order.service.OrderPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.ORDER_API)
public class OrderController {

    private final OrderPublicService service;

    @PostMapping
    public ResponseEntity<OrderPublicResponse> placeOrder(@RequestBody OrderPublicRequest orderPublicRequest){
        OrderPublicResponse response = service.placeOrder(orderPublicRequest);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{orderCode}/payment-submission")
    public ResponseEntity<PaymentSubmissionPublicResponse> submitPayment(
            @PathVariable String orderCode,
            @RequestBody PaymentSubmissionPublicRequest request
    ) {
        return ResponseEntity.ok(service.submitPayment(orderCode, request));
    }
    @GetMapping("/{orderCode}")
    ResponseEntity<OrderPublicResponse> getOrderByCode(@PathVariable String orderCode){
        OrderPublicResponse response = service.getOrderByCode(orderCode);
        return ResponseEntity.ok().body(response);
    }

}
