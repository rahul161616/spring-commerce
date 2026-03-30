package com.jugger.springcommerce.modules.homepage.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryAdminResponse;
import com.jugger.springcommerce.modules.homepage.service.HomepageFeaturedCategoryAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.HOMEPAGE_ADMIN_FEATURED_CATEGORY)
@RequiredArgsConstructor
public class HomepageFeaturedCategoryAdminController {

    private final HomepageFeaturedCategoryAdminService homepageFeaturedCategoryService;
    @PostMapping
    public ResponseEntity<HomepageFeaturedCategoryAdminResponse> addFeaturedCategory(@RequestBody HomepageFeaturedCategoryAdminRequest request){
        HomepageFeaturedCategoryAdminResponse response = homepageFeaturedCategoryService.addHomePageFeaturedCategory(request);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HomepageFeaturedCategoryAdminResponse> getFeaturedCategoryByIdForAdmin(@PathVariable Long id){
        HomepageFeaturedCategoryAdminResponse response = homepageFeaturedCategoryService.getFeaturedCategoryByIdForAdmin(id);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping
    public ResponseEntity<List<HomepageFeaturedCategoryAdminResponse>> getFeaturedCategoriesForAdmin(){
        List<HomepageFeaturedCategoryAdminResponse> response = homepageFeaturedCategoryService.getFeaturedCategoriesForAdmin();
        return ResponseEntity.ok().body(response);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<HomepageFeaturedCategoryAdminResponse> updateFeaturedCategoryByAdmin(@PathVariable Long id,@RequestBody HomepageFeaturedCategoryAdminRequest request){
        HomepageFeaturedCategoryAdminResponse response = homepageFeaturedCategoryService.updateFeaturedCategoryByAdmin(id,request);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeaturedCategory(@PathVariable Long id){
        homepageFeaturedCategoryService.deleteFeaturedCategory(id);
        return ResponseEntity.noContent().build();
    }
}
