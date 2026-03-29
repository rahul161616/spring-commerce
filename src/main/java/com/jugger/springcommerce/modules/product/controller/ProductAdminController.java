package com.jugger.springcommerce.modules.product.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.product.dto.admin.CreateProductRequest;
import com.jugger.springcommerce.modules.product.dto.admin.ProductAdminResponse;
import com.jugger.springcommerce.modules.product.service.ProductAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/product")
    public ResponseEntity<ProductAdminResponse> getProductByIdForAdmin(@PathVariable("id") Long id){
        ProductAdminResponse response = productAdminService.getProductByIdForAdmin(id);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/{id}/product")
    public ResponseEntity<Void> softDeleteProduct(@PathVariable("id") Long id){
        productAdminService.softDeleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
