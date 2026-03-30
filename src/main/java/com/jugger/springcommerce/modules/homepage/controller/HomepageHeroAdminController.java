package com.jugger.springcommerce.modules.homepage.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroAdminResponse;
import com.jugger.springcommerce.modules.homepage.service.HomepageHeroAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.HOMEPAGE_ADMIN_HERO)
@RequiredArgsConstructor
public class HomepageHeroAdminController {

    private final HomepageHeroAdminService homePageHeroAdminService;
    @PostMapping
    public ResponseEntity<HomepageHeroAdminResponse> addHero(@RequestBody HomepageHeroAdminRequest request){
        HomepageHeroAdminResponse response = homePageHeroAdminService.addHero(request);
        return ResponseEntity.ok().body(response);
    }
    @PatchMapping("/{id}/hero")
    public ResponseEntity<HomepageHeroAdminResponse> updateHeroByAdmin(@PathVariable Long id,@RequestBody HomepageHeroAdminRequest request){
        HomepageHeroAdminResponse response = homePageHeroAdminService.updateHeroByAdmin(id,request);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping()
    public ResponseEntity<List<HomepageHeroAdminResponse>> getHeroForAdmin(){
        List<HomepageHeroAdminResponse> response = homePageHeroAdminService.getHeroForAdmin();
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}/hero")
    public ResponseEntity<HomepageHeroAdminResponse> getHeroByIdForAdmin(@PathVariable Long id){
        HomepageHeroAdminResponse response = homePageHeroAdminService.getHeroByIdForAdmin(id);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/{id}/hero")
    public ResponseEntity<Void> deleteHeroByAdmin(@PathVariable Long id){
        homePageHeroAdminService.deleteHeroByAdmin(id);
        return ResponseEntity.noContent().build();
    }

}
