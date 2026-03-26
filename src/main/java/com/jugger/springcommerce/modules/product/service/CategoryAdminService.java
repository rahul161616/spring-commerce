package com.jugger.springcommerce.modules.product.service;

import com.jugger.springcommerce.modules.product.dto.admin.CategoryAdminResponse;
import com.jugger.springcommerce.modules.product.dto.admin.CreateCategoryAdminRequest;

import java.util.List;

public interface CategoryAdminService {

    CategoryAdminResponse addCategory(CreateCategoryAdminRequest createCategoryAdminRequest);
    List<CategoryAdminResponse> getAllCategoriesforAdmin();
    List<CategoryAdminResponse> getAllCategoriesOptionsForAdmin();
}
