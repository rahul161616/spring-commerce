package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryPublicResponse;
import com.jugger.springcommerce.modules.homepage.mapper.HomepageFeaturedCategoryPublicMapper;
import com.jugger.springcommerce.modules.homepage.service.HomepageFeaturedCategoryPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageFeaturedCategoryPublicServiceImpl implements HomepageFeaturedCategoryPublicService {
    private final JdbcTemplate jdbcTemplate;
    private final HomepageFeaturedCategoryPublicMapper mapper;

    @Override
    public List<HomepageFeaturedCategoryPublicResponse> getFeaturedCategories(){
        String sql = """
                SELECT h.id,
                c.name,
                h.caption,
                '/categories/' || c.slug AS href,
                h.image_url,
                h.emphasis
                FROM homepage_featured_categories h
                JOIN categories c ON c.id = h.category_id
                WHERE h.is_active = true
                ORDER BY h.display_order ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapper.mapToPublicResponse(rs));
    }
}
