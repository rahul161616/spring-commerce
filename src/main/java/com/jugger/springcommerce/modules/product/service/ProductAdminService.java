package com.jugger.springcommerce.modules.product.service;

import com.jugger.springcommerce.modules.product.dto.admin.*;

import java.util.List;

public interface ProductAdminService {
    ProductAdminResponse createProduct(CreateProductRequest request);
    List<ProductAdminResponse> getAllProductsForAdmin();
    ProductAdminResponse getProductByIdForAdmin(Long id);
    void softDeleteProductById(Long id);
    ProductAdminResponse updateProductByAdmin(Long id, UpdateProductAdminRequest request);
    UpdateStatusResponseForAdmin updateProductStatusByAdmin(UpdateStatusRequestByAdmin updateStatusRequestByAdmin);
}
