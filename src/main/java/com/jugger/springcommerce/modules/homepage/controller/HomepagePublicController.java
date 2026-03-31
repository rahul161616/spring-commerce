package com.jugger.springcommerce.modules.homepage.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.homepage.dto.HomepagePublicResponse;
import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroPublicResponse;
import com.jugger.springcommerce.modules.homepage.service.HomepagePublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.HOMEPAGE_PUBLIC)
public class HomepagePublicController {

    private final HomepagePublicService homepagePublicService;
    @GetMapping
    public ResponseEntity<HomepagePublicResponse> getHomePageResponse(){
        HomepagePublicResponse homepagePublicResponse = homepagePublicService.getHomePageData();
        return ResponseEntity.ok().body(homepagePublicResponse);
    }
}
