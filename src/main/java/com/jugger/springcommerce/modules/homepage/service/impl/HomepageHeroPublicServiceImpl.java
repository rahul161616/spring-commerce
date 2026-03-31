package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroPublicResponse;
import com.jugger.springcommerce.modules.homepage.mapper.HomepageHeroPublicMapper;
import com.jugger.springcommerce.modules.homepage.service.HomepageHeroPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageHeroPublicServiceImpl implements HomepageHeroPublicService {
    private final JdbcTemplate jdbcTemplate;
    private final HomepageHeroPublicMapper mapper;

    @Override
    public List<HomepageHeroPublicResponse> getHero(){
        String sql = """
                SELECT h.id,
                h.eyebrow,
                h.title,
                h.cta_label,
                h.cta_href,
                h.image_url,
                h.linked_product_id,
                h.linked_category_id
                FROM homepage_hero h
                WHERE h.is_active = true
                ORDER BY h.display_order ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapper.mapToPublicResponse(rs));
    }
}
