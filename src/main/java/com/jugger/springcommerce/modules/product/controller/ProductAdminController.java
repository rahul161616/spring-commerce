package com.jugger.springcommerce.modules.product.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.product.dto.admin.CreateProductRequest;
import com.jugger.springcommerce.modules.product.dto.admin.ProductAdminResponse;
import com.jugger.springcommerce.modules.product.service.ProductAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.ADMIN_PRODUCT_API)
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @PostMapping("/create-product")
    public ResponseEntity<ProductAdminResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductAdminResponse response = productAdminService.createProduct(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all-products")
    public ResponseEntity<List<ProductAdminResponse>> getAllProductsForAdmin(){
        List<ProductAdminResponse> response = productAdminService.getAllProductsForAdmin();
        return ResponseEntity.ok().body(response);
    }
}
