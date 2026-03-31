package com.jugger.springcommerce.modules.homepage.service;

import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroPublicResponse;

import java.util.List;

public interface HomepageHeroPublicService {
    List<HomepageHeroPublicResponse> getHero();
}
