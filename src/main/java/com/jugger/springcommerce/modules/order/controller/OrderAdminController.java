package com.jugger.springcommerce.modules.order.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.order.dto.OrderAdminResponse;
import com.jugger.springcommerce.modules.order.dto.OrderVerifyAdminRequest;
import com.jugger.springcommerce.modules.order.dto.OrderVerifyAdminResponse;
import com.jugger.springcommerce.modules.order.service.OrderAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.ADMIN_ORDER_API)
public class OrderAdminController {

    private final OrderAdminService orderAdminService;

    @GetMapping
    public ResponseEntity<List<OrderAdminResponse>> getOrderDetails() {
        return ResponseEntity.ok(orderAdminService.getOrderDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderAdminResponse> getOrderDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(orderAdminService.getOrderDetailsById(id));
    }

    @PatchMapping("/verify")
    public ResponseEntity<OrderVerifyAdminResponse> verifyOrder(@Valid @RequestBody OrderVerifyAdminRequest request) {
        return ResponseEntity.ok(orderAdminService.verifyOrder(request));
    }

    @PatchMapping("/process")
    public ResponseEntity<OrderVerifyAdminResponse> processOrder(@Valid @RequestBody OrderVerifyAdminRequest request) {
        return ResponseEntity.ok(orderAdminService.processOrder(request));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<OrderVerifyAdminResponse> cancelOrder(@Valid @RequestBody OrderVerifyAdminRequest request) {
        return ResponseEntity.ok(orderAdminService.cancelOrder(request));
    }
}
