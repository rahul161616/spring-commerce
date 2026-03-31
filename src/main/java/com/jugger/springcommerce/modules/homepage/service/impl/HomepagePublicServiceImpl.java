package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.modules.homepage.dto.HomepagePublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryPublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroPublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalPublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductPublicResponse;
import com.jugger.springcommerce.modules.homepage.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepagePublicServiceImpl implements HomepagePublicService {

    private final HomepageHeroPublicService homepageHeroPublicService;
    private final HomepageTrendingProductPublicService homepageTrendingProductPublicService;
    private final HomepageFeaturedCategoryPublicService homepageFeaturedCategoryPublicService;
    private final HomepageNewArrivalPublicService homepageNewArrivalPublicService;

    @Override
    public HomepagePublicResponse getHomePageData(){
        String brandName = "Shades | Verse";
        String quote = "Style is what we present ourselves as";
        String quoteCaption = "Shades | Verse";
        List<HomepageHeroPublicResponse> hero = homepageHeroPublicService.getHero();
        List<HomepageFeaturedCategoryPublicResponse> categories = homepageFeaturedCategoryPublicService.getFeaturedCategories();
        List<HomepageTrendingProductPublicResponse> trendingProducts = homepageTrendingProductPublicService.getHomepageTrendingProductPublicService();
        List<HomepageNewArrivalPublicResponse> response = homepageNewArrivalPublicService.getNewArrival();

        return  HomepagePublicResponse.builder()
                .brandName(brandName)
                .quote(quote)
                .quoteCaption(quoteCaption)
                .heroSlides(hero)
                .featuredCategories(categories)
                .trendingProducts(trendingProducts)
                .newArrivals(response)
                .build();
    }
}
