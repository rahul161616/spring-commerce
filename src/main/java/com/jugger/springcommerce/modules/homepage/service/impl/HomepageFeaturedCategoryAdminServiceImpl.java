package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryAdminResponse;
import com.jugger.springcommerce.modules.homepage.mapper.HomepageFeaturedCategoryAdminMapper;
import com.jugger.springcommerce.modules.homepage.model.HomepageFeaturedCategory;
import com.jugger.springcommerce.modules.homepage.repository.HomepageFeaturedCategoryRepository;
import com.jugger.springcommerce.modules.homepage.service.HomepageFeaturedCategoryAdminService;
import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageFeaturedCategoryAdminServiceImpl implements HomepageFeaturedCategoryAdminService {
    private final HomepageFeaturedCategoryRepository homepageFeaturedCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final HomepageFeaturedCategoryAdminMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public HomepageFeaturedCategoryAdminResponse addHomePageFeaturedCategory(HomepageFeaturedCategoryAdminRequest request){

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (request.getCategoryId() == null) {
            throw new IllegalArgumentException("Category id cannot be null");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        HomepageFeaturedCategory featuredCategory = HomepageFeaturedCategory.builder()
            .caption(request.getCaption())
            .imageUrl(request.getImageUrl())
            .emphasis(request.getEmphasis())
            .isActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE)
            .displayOrder(nextDisplayOrder())
            .category(category)
            .build();

        HomepageFeaturedCategory savedFeaturedCategory = homepageFeaturedCategoryRepository.save(featuredCategory);
        return mapper.mapToAdminResponse(savedFeaturedCategory);
    }

    @Override
    public HomepageFeaturedCategoryAdminResponse getFeaturedCategoryByIdForAdmin(Long id){

        String sql = """
                SELECT h.id,
                h.caption,
                h.image_url,
                h.emphasis,
                h.is_active,
                h.display_order,
                h.category_id,
                c.name AS category_name
                FROM homepage_featured_categories h
                JOIN categories c ON c.id = h.category_id
                WHERE h.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, (rs,rowNum) -> mapper.mapToAdminResponse(rs), id);
    }

    @Override
    public List<HomepageFeaturedCategoryAdminResponse> getFeaturedCategoriesForAdmin(){
        String sql = """
                SELECT h.id,
                h.caption,
                h.image_url,
                h.emphasis,
                h.is_active,
                h.display_order,
                h.category_id,
                c.name AS category_name
                FROM homepage_featured_categories h
                JOIN categories c ON c.id = h.category_id
                ORDER BY h.display_order ASC
                """;
        return jdbcTemplate.query(sql,(rs,rowNum)->mapper.mapToAdminResponse(rs));
    }


    @Override
    public HomepageFeaturedCategoryAdminResponse updateFeaturedCategoryByAdmin(Long id, HomepageFeaturedCategoryAdminRequest request){
        if (id == null) {
            throw new IllegalArgumentException("Featured category id cannot be null");
        }

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        HomepageFeaturedCategory featuredCategory = homepageFeaturedCategoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Homepage featured category not found with id: " + id));

        if (request.getCaption() != null) {
            featuredCategory.setCaption(request.getCaption());
        }
        if (request.getImageUrl() != null) {
            featuredCategory.setImageUrl(request.getImageUrl());
        }
        if (request.getEmphasis() != null) {
            featuredCategory.setEmphasis(request.getEmphasis());
        }
        if (request.getIsActive() != null) {
            featuredCategory.setIsActive(request.getIsActive());
        }
        if (request.getDisplayOrder() != null) {
            featuredCategory.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
            featuredCategory.setCategory(category);
        }

        HomepageFeaturedCategory updatedFeaturedCategory = homepageFeaturedCategoryRepository.save(featuredCategory);
        return mapper.mapToAdminResponse(updatedFeaturedCategory);
    }

    @Override
    public void deleteFeaturedCategory(Long id){
        HomepageFeaturedCategory category = homepageFeaturedCategoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Homepage Featured Category not found with id: " + id)
        );
        if(category.getIsActive()==true){
            category.setIsActive(false);
            homepageFeaturedCategoryRepository.save(category);
        }
    }

    private Integer nextDisplayOrder() {
        HomepageFeaturedCategory latestFeaturedCategory = homepageFeaturedCategoryRepository.findTopByOrderByDisplayOrderDesc();
        if (latestFeaturedCategory == null || latestFeaturedCategory.getDisplayOrder() == null) {
            return 1;
        }

        return latestFeaturedCategory.getDisplayOrder() + 1;
    }

}
