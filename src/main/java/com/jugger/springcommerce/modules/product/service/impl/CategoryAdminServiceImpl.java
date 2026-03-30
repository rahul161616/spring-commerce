package com.jugger.springcommerce.modules.product.service.impl;

import com.jugger.springcommerce.common.exception.BusinessException;
import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.product.dto.admin.CategoryAdminResponse;
import com.jugger.springcommerce.modules.product.dto.admin.CreateCategoryAdminRequest;
import com.jugger.springcommerce.modules.product.mapper.CategoryMapper;
import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.repository.CategoryRepository;
import com.jugger.springcommerce.modules.product.service.CategoryAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public CategoryAdminResponse addCategory(CreateCategoryAdminRequest createCategoryAdminRequest) {
        String slug = generateUniqueSlug(createCategoryAdminRequest.getName(), null);

        Category parent = null;
        if (createCategoryAdminRequest.getParentId() != null) {
            parent = categoryRepository.findById(createCategoryAdminRequest.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
        }

        Category category = Category.builder()
                .name(createCategoryAdminRequest.getName())
                .slug(slug)
                .parent(parent)
                .description(createCategoryAdminRequest.getDescription())
                .isActive(createCategoryAdminRequest.getIsActive() != null ? createCategoryAdminRequest.getIsActive() : true)
                .build();
        categoryRepository.save(category);
        return categoryMapper.mapToCategoryAdminResponse(category);
    }

    private String generateUniqueSlug(String input, Long currentCategoryId) {
        String baseSlug = toSlug(input);
        String candidate = baseSlug;
        int counter = 1;

        while (true) {
            Category existing = categoryRepository.findBySlug(candidate).orElse(null);

            if (existing == null) {
                return candidate;
            }

            if (currentCategoryId != null && existing.getId().equals(currentCategoryId)) {
                return candidate;
            }

            candidate = baseSlug + "-" + counter++;
        }
    }

    private String toSlug(String input) {
        if (input == null || input.isBlank()) {
            throw new BusinessException("Category name is required for slug generation");
        }

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        String slug = normalized
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-");

        if (slug.isBlank()) {
            throw new BusinessException("Invalid category name for slug generation");
        }

        return slug;
    }

    @Override
    public List<CategoryAdminResponse> getAllCategoriesforAdmin() {
        String sql = """
                SELECT c.id,
                       c.name,
                       c.slug,
                       c.description,
                       c.is_active,
                       c.parent_id,
                       p.name AS parent_name
                FROM categories c
                LEFT JOIN categories p ON p.id = c.parent_id
                ORDER BY c.id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> categoryMapper.mapRowToAdminResponse(rs));
    }

    @Override
    public List<CategoryAdminResponse> getAllCategoriesOptionsForAdmin() {
        String sql = """
                SELECT c.id,
                       c.name,
                       c.slug
                FROM categories c
                ORDER BY c.id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> categoryMapper.mapRowToAdminResponseForOptions(rs));
    }
    @Override
    public CategoryAdminResponse getCategoryByIdForAdmin(Long id){
        String sql = """
            SELECT c.id,
                   c.name,
                   c.slug
            FROM categories c
            WHERE c.id = ?
            """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> categoryMapper.mapRowToAdminResponseForOptions(rs),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Category not found with id: " + id + " or is already deleted.");
        }
    }
    public CategoryAdminResponse updateCategoryByAdmin(Long id, CreateCategoryAdminRequest createCategoryAdminRequest){
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found or is already deleted.")
        );
        if(createCategoryAdminRequest.getName() != null){
            String slug = generateUniqueSlug(createCategoryAdminRequest.getName(), id);
            category.setName(createCategoryAdminRequest.getName());
            category.setSlug(slug);
        }
        if(createCategoryAdminRequest.getDescription() != null){
            category.setDescription(createCategoryAdminRequest.getDescription());
        }
        if(createCategoryAdminRequest.getParentId() != null){
            category.setParent(categoryRepository.findById(createCategoryAdminRequest.getParentId()).orElseThrow(
                    ()-> new ResourceNotFoundException("Parent category not found.")
            ));
        }
        if(createCategoryAdminRequest.getIsActive() != null){
            category.setIsActive(createCategoryAdminRequest.getIsActive());
        }
        return categoryMapper.mapToCategoryAdminResponse(categoryRepository.save(category));
    }
}

