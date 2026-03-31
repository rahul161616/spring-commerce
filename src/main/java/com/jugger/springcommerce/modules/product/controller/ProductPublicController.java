package com.jugger.springcommerce.modules.product.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.product.dto.ProductPublicResponse;
import com.jugger.springcommerce.modules.product.service.ProductPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(ApiConstants.PUBLIC_PRODUCT_API)
@RequiredArgsConstructor
@RestController
public class ProductPublicController {
    private final ProductPublicService productPublicService;

    @GetMapping
    public ResponseEntity<List<ProductPublicResponse>>getAllProducts(){
        List<ProductPublicResponse> response =productPublicService.getAllProducts();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductPublicResponse>getProductById(@PathVariable Long id){
        ProductPublicResponse response = productPublicService.getProductById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductPublicResponse> getProductBySlug(@PathVariable String slug) {
        ProductPublicResponse response = productPublicService.getProductBySlug(slug);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/category/{slug}")
    public ResponseEntity<List<ProductPublicResponse>> getProductsByCategorySlug(@PathVariable String slug) {
        List<ProductPublicResponse> response = productPublicService.getProductByCategorySlug(slug);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/tag/{slug}")
    public ResponseEntity<List<ProductPublicResponse>> getProductsByTagSlug(@PathVariable String slug) {
        List<ProductPublicResponse> response = productPublicService.getProductByTagSlug(slug);
        return ResponseEntity.ok().body(response);
    }
}
