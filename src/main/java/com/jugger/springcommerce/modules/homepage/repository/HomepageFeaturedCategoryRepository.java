package com.jugger.springcommerce.modules.homepage.repository;

import com.jugger.springcommerce.modules.homepage.model.HomepageFeaturedCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomepageFeaturedCategoryRepository extends JpaRepository<HomepageFeaturedCategory,Long> {
    HomepageFeaturedCategory findTopByOrderByDisplayOrderDesc();
}
