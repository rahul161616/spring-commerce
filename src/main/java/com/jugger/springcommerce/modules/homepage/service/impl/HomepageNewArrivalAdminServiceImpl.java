package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalAdminResponse;
import com.jugger.springcommerce.modules.homepage.mapper.HomepageNewArrivalAdminMapper;
import com.jugger.springcommerce.modules.homepage.model.HomepageNewArrivalRule;
import com.jugger.springcommerce.modules.homepage.repository.HomepageNewArrivalRepository;
import com.jugger.springcommerce.modules.homepage.service.HomepageNewArrivalAdminService;
import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.model.Tag;
import com.jugger.springcommerce.modules.product.repository.CategoryRepository;
import com.jugger.springcommerce.modules.product.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageNewArrivalAdminServiceImpl implements HomepageNewArrivalAdminService {
    private final HomepageNewArrivalAdminMapper mapper;
    private final JdbcTemplate jdbcTemplate;
    private final HomepageNewArrivalRepository homepageNewArrivalRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    public HomepageNewArrivalAdminResponse addNewArrival(HomepageNewArrivalAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (request.getLimitCount() == null || request.getLimitCount() <= 0) {
            throw new IllegalArgumentException("Limit count must be greater than zero");
        }

        Category category = resolveCategory(request.getCategoryId());
        Tag tag = resolveTag(request.getTagId());

        HomepageNewArrivalRule rule = HomepageNewArrivalRule.builder()
                .limitCount(request.getLimitCount())
                .category(category)
                .tag(tag)
                .onlyActive(request.getOnlyActive() != null ? request.getOnlyActive() : Boolean.TRUE)
                .isActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        HomepageNewArrivalRule savedRule = homepageNewArrivalRepository.save(rule);
        return mapper.mapToAdminResponse(savedRule);
    }

    @Override
    public HomepageNewArrivalAdminResponse getNewArrivalByIdForAdmin(Long id) {
        String sql = """
                SELECT h.id,
                       h.limit_count,
                       h.category_id,
                       c.name AS category_name,
                       h.tag_id,
                       t.name AS tag_name,
                       h.only_active,
                       h.is_active
                FROM homepage_new_arrivals_rule h
                LEFT JOIN categories c ON c.id = h.category_id
                LEFT JOIN tags t ON t.id = h.tag_id
                WHERE h.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapper.mapToAdminResponse(rs), id);
    }

    @Override
    public List<HomepageNewArrivalAdminResponse> getNewArrivalForAdmin() {
        String sql = """
                SELECT h.id,
                       h.limit_count,
                       h.category_id,
                       c.name AS category_name,
                       h.tag_id,
                       t.name AS tag_name,
                       h.only_active,
                       h.is_active
                FROM homepage_new_arrivals_rule h
                LEFT JOIN categories c ON c.id = h.category_id
                LEFT JOIN tags t ON t.id = h.tag_id
                ORDER BY h.id ASC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapper.mapToAdminResponse(rs));
    }

    @Override
    public HomepageNewArrivalAdminResponse updateHomepageNewArrival(Long id, HomepageNewArrivalAdminRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("New arrival rule id cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        HomepageNewArrivalRule rule = homepageNewArrivalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Homepage new arrival rule not found with id: " + id));

        if (request.getLimitCount() != null) {
            if (request.getLimitCount() <= 0) {
                throw new IllegalArgumentException("Limit count must be greater than zero");
            }
            rule.setLimitCount(request.getLimitCount());
        }
        if (request.getCategoryId() != null) {
            rule.setCategory(resolveCategory(request.getCategoryId()));
        }
        if (request.getTagId() != null) {
            rule.setTag(resolveTag(request.getTagId()));
        }
        if (request.getOnlyActive() != null) {
            rule.setOnlyActive(request.getOnlyActive());
        }
        if (request.getIsActive() != null) {
            rule.setIsActive(request.getIsActive());
        }
        rule.setUpdatedAt(Instant.now());

        HomepageNewArrivalRule updatedRule = homepageNewArrivalRepository.save(rule);
        return mapper.mapToAdminResponse(updatedRule);
    }

    @Override
    public void deleteNewArrival(Long id) {
        HomepageNewArrivalRule rule = homepageNewArrivalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Homepage new arrival rule not found with id: " + id));
        rule.setIsActive(Boolean.FALSE);
        homepageNewArrivalRepository.save(rule);
    }

    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }

    private Tag resolveTag(Long tagId) {
        if (tagId == null) {
            return null;
        }
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));
    }
}
