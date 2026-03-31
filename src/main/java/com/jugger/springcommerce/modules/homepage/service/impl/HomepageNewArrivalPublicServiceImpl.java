package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalPublicResponse;
import com.jugger.springcommerce.modules.homepage.mapper.HomepageNewArrivalPublicMapper;
import com.jugger.springcommerce.modules.homepage.service.HomepageNewArrivalPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HomepageNewArrivalPublicServiceImpl implements HomepageNewArrivalPublicService {
    private final JdbcTemplate jdbcTemplate;
    private final HomepageNewArrivalPublicMapper mapper;

    @Override
    public List<HomepageNewArrivalPublicResponse> getNewArrival() {
        String ruleSql = """
                SELECT id,
                limit_count,
                category_id,
                tag_id,
                only_active
                FROM homepage_new_arrivals_rule
                WHERE is_active = true
                ORDER BY id DESC
                LIMIT 1
                """;

        List<Map<String, Object>> rules = jdbcTemplate.queryForList(ruleSql);
        if (rules.isEmpty()) {
            return List.of();
        }

        Map<String, Object> rule = rules.get(0);
        Integer limitCount = ((Number) rule.get("limit_count")).intValue();
        Long categoryId = rule.get("category_id") != null ? ((Number) rule.get("category_id")).longValue() : null;
        Long tagId = rule.get("tag_id") != null ? ((Number) rule.get("tag_id")).longValue() : null;
        Boolean onlyActive = rule.get("only_active") != null ? (Boolean) rule.get("only_active") : Boolean.TRUE;

        StringBuilder productSql = new StringBuilder("""
                SELECT p.id,
                c.name AS family,
                p.name AS title,
                p.price::text AS price,
                p.compare_at::text AS compare_at,
                COALESCE(pi.image_url, '') AS image,
                '/products/' || p.slug AS href
                FROM products p
                JOIN categories c ON c.id = p.category_id
                LEFT JOIN LATERAL (
                    SELECT image_url
                    FROM product_images
                    WHERE product_id = p.id
                    ORDER BY is_primary DESC, display_order ASC, id ASC
                    LIMIT 1
                ) pi ON true
                WHERE 1 = 1
                """);

        List<Object> params = new ArrayList<>();

        if (Boolean.TRUE.equals(onlyActive)) {
            productSql.append(" AND p.status = 'ACTIVE'");
        }

        if (categoryId != null) {
            productSql.append(" AND p.category_id = ?");
            params.add(categoryId);
        }

        if (tagId != null) {
            productSql.append("""
                    AND EXISTS (
                        SELECT 1
                        FROM product_tags pt
                        WHERE pt.product_id = p.id
                        AND pt.tag_id = ?
                    )
                    """);
            params.add(tagId);
        }

        productSql.append("""
                ORDER BY p.created_at DESC, p.id DESC
                LIMIT ?
                """);
        params.add(limitCount);

        return jdbcTemplate.query(
                productSql.toString(),
                (rs, rowNum) -> mapper.mapToPublicResponse(rs),
                params.toArray()
        );
    }
}
