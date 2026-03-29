package com.jugger.springcommerce.modules.product.service;

import com.jugger.springcommerce.modules.product.dto.admin.CreateProductRequest;
import com.jugger.springcommerce.modules.product.dto.admin.ProductAdminResponse;

import java.util.List;

public interface ProductAdminService {
    ProductAdminResponse createProduct(CreateProductRequest request);
    List<ProductAdminResponse> getAllProductsForAdmin();
    ProductAdminResponse getProductByIdForAdmin(Long id);
    void softDeleteProductById(Long id);
}
