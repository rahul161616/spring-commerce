package com.jugger.springcommerce.modules.product.service.impl;

import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.product.dto.ProductPublicResponse;
import com.jugger.springcommerce.modules.product.mapper.ProductPublicMapper;
import com.jugger.springcommerce.modules.product.service.ProductPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPublicServiceImpl implements ProductPublicService {
    private final JdbcTemplate jdbcTemplate;
    private final ProductPublicMapper productPublicMapper;

    private static final String PUBLIC_PRODUCT_SELECT = """
            SELECT
                p.id,
                p.name,
                p.slug,
                p.price,
                p.compare_at,
                p.description,
                p.short_description,
                p.stock_quantity,
                c.name AS category_name,
                COALESCE(tag_data.tag_names, '') AS tag_names,
                COALESCE(tag_data.tag_ids, '') AS tag_ids,
                COALESCE(image_data.image_url, '') AS image_url
            FROM products p
            JOIN categories c ON c.id = p.category_id
            LEFT JOIN LATERAL (
                SELECT
                    STRING_AGG(t.name, ', ' ORDER BY t.name) AS tag_names,
                    STRING_AGG(t.id::text, ',' ORDER BY t.id) AS tag_ids
                FROM product_tags pt
                JOIN tags t ON t.id = pt.tag_id
                WHERE pt.product_id = p.id
            ) tag_data ON true
            LEFT JOIN LATERAL (
                SELECT pi.image_url
                FROM product_images pi
                WHERE pi.product_id = p.id
                ORDER BY pi.is_primary DESC, pi.display_order ASC, pi.id ASC
                LIMIT 1
            ) image_data ON true
            """;

    @Override
    public ProductPublicResponse getProductById(Long id){
        String sql = PUBLIC_PRODUCT_SELECT + """
                WHERE p.id = ?
                  AND p.status = 'ACTIVE'
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> productPublicMapper.mapToPublicResponse(rs),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Active product not found with id: " + id);
        }
    }

    @Override
    public ProductPublicResponse getProductBySlug(String slug) {
        String sql = PUBLIC_PRODUCT_SELECT + """
                WHERE p.slug = ?
                  AND p.status = 'ACTIVE'
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> productPublicMapper.mapToPublicResponse(rs),
                    slug
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Active product not found with slug: " + slug);
        }
    }

    @Override
    public List<ProductPublicResponse> getProductByCategorySlug(String slug) {
        String sql = PUBLIC_PRODUCT_SELECT + """
                WHERE c.slug = ?
                  AND c.is_active = true
                  AND p.status = 'ACTIVE'
                ORDER BY p.created_at DESC, p.id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> productPublicMapper.mapToPublicResponse(rs),
                slug
        );
    }

    @Override
    public List<ProductPublicResponse> getProductByTagSlug(String slug) {
        String sql = PUBLIC_PRODUCT_SELECT + """
                WHERE p.status = 'ACTIVE'
                  AND EXISTS (
                      SELECT 1
                      FROM product_tags pt_filter
                      JOIN tags t_filter ON t_filter.id = pt_filter.tag_id
                      WHERE pt_filter.product_id = p.id
                        AND t_filter.slug = ?
                        AND t_filter.is_active = true
                  )
                ORDER BY p.created_at DESC, p.id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> productPublicMapper.mapToPublicResponse(rs),
                slug
        );
    }

    @Override
    public List<ProductPublicResponse> getAllProducts(){
        String sql = PUBLIC_PRODUCT_SELECT + """
                WHERE p.status = 'ACTIVE'
                ORDER BY p.created_at DESC, p.id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> productPublicMapper.mapToPublicResponse(rs));
    }
}
