package com.jugger.springcommerce.modules.homepage.service;

import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryAdminResponse;

import java.util.List;

public interface HomepageFeaturedCategoryAdminService {
    HomepageFeaturedCategoryAdminResponse addHomePageFeaturedCategory(HomepageFeaturedCategoryAdminRequest request);

    List<HomepageFeaturedCategoryAdminResponse> getFeaturedCategoriesForAdmin();

    HomepageFeaturedCategoryAdminResponse updateFeaturedCategoryByAdmin(Long id, HomepageFeaturedCategoryAdminRequest request);

    void deleteFeaturedCategory(Long id);

    HomepageFeaturedCategoryAdminResponse getFeaturedCategoryByIdForAdmin(Long id);
}
