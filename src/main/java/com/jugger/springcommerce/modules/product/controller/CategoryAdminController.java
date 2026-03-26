package com.jugger.springcommerce.modules.product.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.product.dto.admin.CategoryAdminResponse;
import com.jugger.springcommerce.modules.product.dto.admin.CreateCategoryAdminRequest;
import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.service.CategoryAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.ADMIN_CATEGORIES_API)
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryAdminService categoryAdminService;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryAdminResponse> addCategory(@RequestBody CreateCategoryAdminRequest createCategoryAdminRequest){

        CategoryAdminResponse categoryAdminResponse = categoryAdminService.addCategory(createCategoryAdminRequest);
        return ResponseEntity.ok().body(categoryAdminResponse);
    }
    @GetMapping("/all-categories")
    public ResponseEntity<List<CategoryAdminResponse>> getAllCategoriesForAdmin(){

        List<CategoryAdminResponse> categoryAdminResponse = categoryAdminService.getAllCategoriesforAdmin();
        return ResponseEntity.ok().body(categoryAdminResponse);
    }
    @GetMapping("/parent-options")
    public ResponseEntity<List<CategoryAdminResponse>> getAllCategoriesOptionsForAdmin(){
        List<CategoryAdminResponse> categoryAdminResponse = categoryAdminService.getAllCategoriesOptionsForAdmin();
        return ResponseEntity.ok().body(categoryAdminResponse);
    }
}
