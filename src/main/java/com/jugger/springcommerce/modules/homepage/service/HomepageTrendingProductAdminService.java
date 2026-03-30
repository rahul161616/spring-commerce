package com.jugger.springcommerce.modules.homepage.service;

import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductAdminResponse;

import java.util.List;

public interface HomepageTrendingProductAdminService {
    HomepageTrendingProductAdminResponse addHomePageTrendingProduct(HomepageTrendingProductAdminRequest request);

    HomepageTrendingProductAdminResponse getTrendingProductByIdForAdmin(Long id);

    List<HomepageTrendingProductAdminResponse> getTrendingProductForAdmin();

    HomepageTrendingProductAdminResponse updateTrendingProduct(Long id, HomepageTrendingProductAdminRequest request);

    void deleteTrendingProduct(Long id);
}
