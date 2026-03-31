package com.jugger.springcommerce.modules.product.service;

import com.jugger.springcommerce.modules.product.dto.ProductPublicResponse;

import java.util.List;

public interface ProductPublicService {
    ProductPublicResponse getProductById(Long id);
    ProductPublicResponse getProductBySlug(String slug);
    List<ProductPublicResponse> getProductByCategorySlug(String slug);
    List<ProductPublicResponse> getProductByTagSlug(String slug);
    List<ProductPublicResponse> getAllProducts();
}
