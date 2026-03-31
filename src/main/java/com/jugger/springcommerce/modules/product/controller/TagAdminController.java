package com.jugger.springcommerce.modules.product.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.product.dto.admin.CreateTagAdminRequest;
import com.jugger.springcommerce.modules.product.dto.admin.TagAdminResponse;
import com.jugger.springcommerce.modules.product.model.Tag;
import com.jugger.springcommerce.modules.product.service.TagAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.ADMIN_TAGS_API)
@RequiredArgsConstructor
public class TagAdminController {

    private final TagAdminService tagAdminService;
    @PostMapping("/create-tag")
    public ResponseEntity<TagAdminResponse> createTag(@RequestBody CreateTagAdminRequest createTagAdminRequest){
        TagAdminResponse tagAdminResponse = tagAdminService.addTag(createTagAdminRequest);
        return ResponseEntity.ok().body(tagAdminResponse);
    }
    @GetMapping("/all-tags")
    public ResponseEntity<List<TagAdminResponse>> getAllTagsForAdmin(){
        List<TagAdminResponse> tagAdminResponse = tagAdminService.getAllTagsForAdmin();
        return ResponseEntity.ok().body(tagAdminResponse);
    }
    @GetMapping("/{id}/tag")
    public ResponseEntity<TagAdminResponse> getTagByIdForAdmin(@PathVariable Long id){
        TagAdminResponse tagAdminResponse = tagAdminService.getTagByIdForAdmin(id);
        return ResponseEntity.ok().body(tagAdminResponse);
    }
    @PatchMapping("/{id}/tag")
    public ResponseEntity<TagAdminResponse> updateTagByAdmin(@PathVariable Long id,@RequestBody CreateTagAdminRequest createTagAdminRequest){
        TagAdminResponse tagAdminResponse = tagAdminService.updateTagByAdmin(id,createTagAdminRequest);
        return ResponseEntity.ok().body(tagAdminResponse);
    }
    @DeleteMapping("/{id}/tag")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long id){
        tagAdminService.deleteTagById(id);
        return ResponseEntity.noContent().build();
    }
}
