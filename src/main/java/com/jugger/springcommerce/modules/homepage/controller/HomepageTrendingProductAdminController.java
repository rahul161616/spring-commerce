package com.jugger.springcommerce.modules.homepage.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductAdminResponse;
import com.jugger.springcommerce.modules.homepage.service.HomepageTrendingProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.HOMEPAGE_ADMIN_TRENDING_PRODUCTS)
@RequiredArgsConstructor
public class HomepageTrendingProductAdminController {

    private final HomepageTrendingProductAdminService homepageTrendingProductAdminService;

    @PostMapping
    public ResponseEntity<HomepageTrendingProductAdminResponse> addTrendingProduct(@RequestBody HomepageTrendingProductAdminRequest request) {
        HomepageTrendingProductAdminResponse response = homepageTrendingProductAdminService.addHomePageTrendingProduct(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomepageTrendingProductAdminResponse> getTrendingProductByIdForAdmin(@PathVariable Long id) {
        HomepageTrendingProductAdminResponse response = homepageTrendingProductAdminService.getTrendingProductByIdForAdmin(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<HomepageTrendingProductAdminResponse>> getTrendingProductForAdmin() {
        List<HomepageTrendingProductAdminResponse> response = homepageTrendingProductAdminService.getTrendingProductForAdmin();
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HomepageTrendingProductAdminResponse> updateTrendingProduct(
            @PathVariable Long id,
            @RequestBody HomepageTrendingProductAdminRequest request
    ) {
        HomepageTrendingProductAdminResponse response = homepageTrendingProductAdminService.updateTrendingProduct(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrendingProduct(@PathVariable Long id) {
        homepageTrendingProductAdminService.deleteTrendingProduct(id);
        return ResponseEntity.noContent().build();
    }
}
