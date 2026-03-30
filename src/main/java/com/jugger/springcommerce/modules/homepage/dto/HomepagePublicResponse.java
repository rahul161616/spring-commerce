package com.jugger.springcommerce.modules.homepage.dto;

import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryPublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroPublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalPublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductPublicResponse;

import java.util.List;

public class HomepagePublicResponse {
    private String brandName;
    private String quote;
    private String quoteCaption;
    private List<HomepageHeroPublicResponse> hero;
    private List<HomepageFeaturedCategoryPublicResponse> featuredCategories;
    private List<HomepageTrendingProductPublicResponse> trendingProducts;
    private List<HomepageNewArrivalPublicResponse> response;
}
