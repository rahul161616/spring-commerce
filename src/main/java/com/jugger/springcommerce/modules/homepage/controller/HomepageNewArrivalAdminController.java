package com.jugger.springcommerce.modules.homepage.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalAdminResponse;
import com.jugger.springcommerce.modules.homepage.service.HomepageNewArrivalAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.HOMEPAGE_ADMIN_NEW_ARRIVAL)
public class HomepageNewArrivalAdminController {
    private final HomepageNewArrivalAdminService homepageNewArrivalAdminService;

    @PostMapping
    public ResponseEntity<HomepageNewArrivalAdminResponse> addNewArrival(@RequestBody HomepageNewArrivalAdminRequest request){
    HomepageNewArrivalAdminResponse homepageNewArrivalAdminResponse = homepageNewArrivalAdminService.addNewArrival(request);
    return ResponseEntity.ok().body(homepageNewArrivalAdminResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HomepageNewArrivalAdminResponse> getNewArrivalByIdForAdmin(@PathVariable Long id){
        HomepageNewArrivalAdminResponse homepageNewArrivalAdminResponse = homepageNewArrivalAdminService.getNewArrivalByIdForAdmin(id);
        return ResponseEntity.ok().body(homepageNewArrivalAdminResponse);
    }
    @GetMapping
    public ResponseEntity<List<HomepageNewArrivalAdminResponse>> getNewArrivalForAdmin(){
        List<HomepageNewArrivalAdminResponse> homepageNewArrivalAdminResponse = homepageNewArrivalAdminService.getNewArrivalForAdmin();
        return ResponseEntity.ok().body(homepageNewArrivalAdminResponse);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<HomepageNewArrivalAdminResponse> updateNewArrival(@PathVariable Long id,@RequestBody HomepageNewArrivalAdminRequest request){
        HomepageNewArrivalAdminResponse homepageNewArrivalAdminResponse = homepageNewArrivalAdminService.updateHomepageNewArrival(id,request);
        return ResponseEntity.ok().body(homepageNewArrivalAdminResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewArrival(@PathVariable Long id){
        homepageNewArrivalAdminService.deleteNewArrival(id);
        return ResponseEntity.noContent().build();
    }
}
