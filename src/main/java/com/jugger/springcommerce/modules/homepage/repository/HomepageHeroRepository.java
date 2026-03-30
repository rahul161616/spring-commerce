package com.jugger.springcommerce.modules.homepage.repository;

import com.jugger.springcommerce.modules.homepage.model.HomepageHero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomepageHeroRepository extends JpaRepository<HomepageHero,Long> {
    HomepageHero findTopByOrderByDisplayOrderDesc();
}
