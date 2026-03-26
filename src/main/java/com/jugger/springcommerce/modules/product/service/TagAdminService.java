package com.jugger.springcommerce.modules.product.service;

import com.jugger.springcommerce.modules.product.dto.admin.CreateTagAdminRequest;
import com.jugger.springcommerce.modules.product.dto.admin.TagAdminResponse;

import java.util.List;

public interface TagAdminService {
    TagAdminResponse addTag(CreateTagAdminRequest createTagAdminRequest);
    List<TagAdminResponse> getAllTagsForAdmin();
}
