package com.jugger.springcommerce.modules.homepage.repository;

import com.jugger.springcommerce.modules.homepage.model.HomepageTrendingProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomepageTrendingProductRepository extends JpaRepository<HomepageTrendingProduct, Long> {
    HomepageTrendingProduct findTopByOrderByDisplayOrderDesc();
}
