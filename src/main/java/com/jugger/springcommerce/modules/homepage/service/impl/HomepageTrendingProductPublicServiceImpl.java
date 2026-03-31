package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductPublicResponse;
import com.jugger.springcommerce.modules.homepage.mapper.HomepageTrendingProductPublicMapper;
import com.jugger.springcommerce.modules.homepage.service.HomepageTrendingProductPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageTrendingProductPublicServiceImpl implements HomepageTrendingProductPublicService {
    private final JdbcTemplate jdbcTemplate;
    private final HomepageTrendingProductPublicMapper mapper;

    @Override
    public List<HomepageTrendingProductPublicResponse> getHomepageTrendingProductPublicService() {
        String sql = """
                SELECT h.id,
                COALESCE(h.label, c.name, 'Trending') AS family,
                p.name AS title,
                p.price::text AS price,
                p.compare_at::text AS compare_at,
                COALESCE(pi.image_url, '') AS image,
                '/products/' || p.slug AS href
                FROM homepage_trending_products h
                JOIN products p ON p.id = h.product_id
                LEFT JOIN categories c ON c.id = p.category_id
                LEFT JOIN LATERAL (
                    SELECT image_url
                    FROM product_images
                    WHERE product_id = p.id
                    ORDER BY is_primary DESC, display_order ASC, id ASC
                    LIMIT 1
                ) pi ON true
                WHERE h.is_active = true
                ORDER BY h.display_order ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapper.mapToPublicResponse(rs));
    }
}
