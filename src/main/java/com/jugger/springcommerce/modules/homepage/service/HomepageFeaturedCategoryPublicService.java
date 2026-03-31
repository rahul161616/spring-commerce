package com.jugger.springcommerce.modules.homepage.service;

import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryPublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductPublicResponse;

import java.util.List;

public interface HomepageFeaturedCategoryPublicService {
    List<HomepageFeaturedCategoryPublicResponse> getFeaturedCategories();
}
