package com.jugger.springcommerce.modules.product.service.impl;

import com.jugger.springcommerce.common.exception.BusinessException;
import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.product.dto.admin.CreateTagAdminRequest;
import com.jugger.springcommerce.modules.product.dto.admin.TagAdminResponse;
import com.jugger.springcommerce.modules.product.mapper.TagMapper;
import com.jugger.springcommerce.modules.product.model.Tag;
import com.jugger.springcommerce.modules.product.repository.TagRepository;
import com.jugger.springcommerce.modules.product.service.TagAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagAdminServiceImpl implements TagAdminService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public TagAdminResponse addTag(CreateTagAdminRequest tagAdminRequest){
        String normalizedTagName = normalizeName(tagAdminRequest.getName());
        String slug = generateUniqueSlug(normalizedTagName,null);
        Tag tag = Tag.builder()
                .name(normalizedTagName)
                .slug(slug)
                .description(tagAdminRequest.getDescription())
                .isActive(tagAdminRequest.getIsActive() != null ? tagAdminRequest.getIsActive() : true)
                .build();
        Tag saved = tagRepository.save(tag);
        return tagMapper.mapToAdminResponse(saved);
    }

    private String normalizeName(String input) {
        String value = input == null ? null : input.trim();
        if (value == null || value.isBlank()) {
            throw new BusinessException("Tag name is required");
        }
        return value;
    }

    private String generateUniqueSlug(String input, Long currentTagId) {
        String baseSlug = toSlug(input);
        String candidate = baseSlug;
        int counter = 1;

        while (true) {
            Tag existing = tagRepository.findBySlug(candidate).orElse(null);

            if (existing == null) {
                return candidate;
            }

            if (currentTagId != null && existing.getId().equals(currentTagId)) {
                return candidate;
            }

            candidate = baseSlug + "-" + counter++;
        }
    }

    private String toSlug(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        String slug = normalized
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-");

        if (slug.isBlank()) {
            throw new BusinessException("Invalid tag name for slug generation");
        }

        return slug;
    }

    @Override
    public List<TagAdminResponse> getAllTagsForAdmin() {
        String sql = """
                SELECT t.id,
                       t.name,
                       t.slug,
                       t.description,
                       t.is_active
                FROM tags t
                ORDER BY t.id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> tagMapper.mapRowToAdminResponse(rs));
    }
    @Override
    public TagAdminResponse getTagByIdForAdmin(Long id) {
        String sql = """
                SELECT t.id,
                       t.name,
                       t.slug,
                       t.description,
                       t.is_active
                FROM tags t
                where t.id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> tagMapper.mapRowToAdminResponse(rs),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Tag not found with id: " + id);
        }
    }
    @Override
    public TagAdminResponse updateTagByAdmin(Long id,CreateTagAdminRequest createTagAdminRequest){
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));

        if (createTagAdminRequest.getName() != null &&
                !createTagAdminRequest.getName().equals(tag.getName())) {

            String slug = generateUniqueSlug(createTagAdminRequest.getName(), id);
            tag.setName(createTagAdminRequest.getName());
        }

        if (createTagAdminRequest.getDescription() != null) {
            tag.setDescription(createTagAdminRequest.getDescription());
        }

        if (createTagAdminRequest.getIsActive() != null) {
            tag.setIsActive(createTagAdminRequest.getIsActive());
        }

        Tag saved = tagRepository.save(tag);
        return tagMapper.mapToAdminResponse(saved);
    }
    public void deleteTagById(Long id){
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        if(tag.getIsActive()==true){
            tag.setIsActive(false);
            tagRepository.save(tag);
        }
    }
}
