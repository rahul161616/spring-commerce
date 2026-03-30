package com.jugger.springcommerce.modules.homepage.service;

import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroAdminResponse;

import java.util.List;

public interface HomepageHeroAdminService {

    HomepageHeroAdminResponse addHero(HomepageHeroAdminRequest request);
    HomepageHeroAdminResponse updateHeroByAdmin(Long id, HomepageHeroAdminRequest request);
    HomepageHeroAdminResponse getHeroByIdForAdmin(Long id);
    List<HomepageHeroAdminResponse> getHeroForAdmin();
    void deleteHeroByAdmin(Long id);
}
