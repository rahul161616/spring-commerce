package com.jugger.springcommerce.modules.homepage.mapper;

import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroPublicResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomepageHeroPublicMapper {

    public HomepageHeroPublicResponse mapToPublicResponse(ResultSet rs) throws SQLException {
        return HomepageHeroPublicResponse.builder()
                .id(rs.getLong("id"))
                .eyebrow(rs.getString("eyebrow"))
                .title(rs.getString("title"))
                .ctaLabel(rs.getString("cta_label"))
                .ctaUrl(rs.getString("cta_href"))
                .imageUrl(rs.getString("image_url"))
                .linkProductId((Long) rs.getObject("linked_product_id"))
                .linkCategoryId((Long) rs.getObject("linked_category_id"))
                .build();
    }
}
