package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroAdminResponse;
import com.jugger.springcommerce.modules.homepage.mapper.HomepageHeroAdminMapper;
import com.jugger.springcommerce.modules.homepage.model.HomepageHero;
import com.jugger.springcommerce.modules.homepage.repository.HomepageHeroRepository;
import com.jugger.springcommerce.modules.homepage.service.HomepageHeroAdminService;
import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.model.Product;
import com.jugger.springcommerce.modules.product.repository.CategoryRepository;
import com.jugger.springcommerce.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageHeroAdminServiceImpl implements HomepageHeroAdminService {

    private final HomepageHeroRepository homepageHeroRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final HomepageHeroAdminMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public HomepageHeroAdminResponse addHero(HomepageHeroAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Product product = resolveProduct(request.getLinkProductId());
        Category category = resolveCategory(request.getLinkCategoryId());
        LocalDateTime now = LocalDateTime.now();

        HomepageHero hero = HomepageHero.builder()
            .eyebrow(request.getEyebrow())
            .title(request.getTitle())
            .supportingText(request.getSupportingText())
            .imageUrl(request.getImageUrl())
            .ctaLabel(request.getCtaLabel())
            .ctaUrl(request.getCtaUrl())
            .isActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE)
            .displayOrder(nextDisplayOrder())
            .createdAt(now)
            .updatedAt(now)
            .product(product)
            .category(category)
            .build();

        HomepageHero savedHero = homepageHeroRepository.save(hero);
        return mapper.mapHeroToAdminResponse(savedHero);
    }

    @Override
    public HomepageHeroAdminResponse updateHeroByAdmin(Long id, HomepageHeroAdminRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Hero id cannot be null");
        }

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        HomepageHero hero = homepageHeroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Homepage hero not found with id: " + id));

        Product product = resolveProduct(request.getLinkProductId());
        Category category = resolveCategory(request.getLinkCategoryId());

        if(request.getEyebrow()!=null){
        hero.setEyebrow(request.getEyebrow());
        }
        if(request.getTitle()!=null){
        hero.setTitle(request.getTitle());
        }
        if(request.getSupportingText()!=null){
        hero.setSupportingText(request.getSupportingText());
        }
        if(request.getImageUrl()!=null){
        hero.setImageUrl(request.getImageUrl());
        }
        if(request.getCtaLabel()!=null){
        hero.setCtaLabel(request.getCtaLabel());
        }
        if(request.getCtaUrl()!=null){
        hero.setCtaUrl(request.getCtaUrl());
        }
        if(request.getIsActive()!=null){
        hero.setIsActive(request.getIsActive());
        }
        if(request.getDisplayOrder()!=null){
        hero.setDisplayOrder(request.getDisplayOrder());
        }
        if (Boolean.TRUE.equals(request.getUnlinkProduct())) {
        hero.setProduct(null);
        } else if(request.getLinkProductId()!=null){
        hero.setProduct(product);
        }
        if (Boolean.TRUE.equals(request.getUnlinkCategory())) {
        hero.setCategory(null);
        } else if(request.getLinkCategoryId()!=null){
        hero.setCategory(category);
        }
        HomepageHero updatedHero = homepageHeroRepository.save(hero);
        return mapper.mapHeroToAdminResponse(updatedHero);
    }

    private Product resolveProduct(Long productId) {
        if (productId == null) {
            return null;
        }

        return productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }

    private Integer nextDisplayOrder() {
        HomepageHero latestHero = homepageHeroRepository.findTopByOrderByDisplayOrderDesc();
        if (latestHero == null || latestHero.getDisplayOrder() == null) {
            return 1;
        }

        return latestHero.getDisplayOrder() + 1;
    }
    @Override
    public List<HomepageHeroAdminResponse> getHeroForAdmin(){
        String sql = """
                SELECT h.id,
                h.eyebrow,
                h.title,
                h.supporting_text,
                h.image_url,
                h.cta_label,
                h.cta_href,
                h.is_active,
                h.display_order,
                h.linked_product_id,
                h.linked_category_id
                FROM homepage_hero h
                ORDER BY h.display_order ASC
                """;

        return jdbcTemplate.query(sql,(rs,rowNum)->mapper.mapHeroToAdminResponse(rs));
    }
    @Override
    public HomepageHeroAdminResponse getHeroByIdForAdmin(Long id){
        String sql = """
                SELECT h.id,
                h.eyebrow,
                h.title,
                h.supporting_text,
                h.image_url,
                h.cta_label,
                h.cta_href,
                h.is_active,
                h.display_order,
                h.linked_product_id,
                h.linked_category_id
                FROM homepage_hero h
                WHERE h.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapper.mapHeroToAdminResponse(rs), id);
    }
    @Override
    public void deleteHeroByAdmin(Long id){
        HomepageHero hero = homepageHeroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Homepage hero not found with id: " + id));
        if(hero.getIsActive()==true){
            hero.setIsActive(false);
            homepageHeroRepository.save(hero);
        }
    }
}
