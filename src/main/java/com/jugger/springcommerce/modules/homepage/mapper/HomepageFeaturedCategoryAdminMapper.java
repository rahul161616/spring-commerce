package com.jugger.springcommerce.modules.homepage.mapper;

import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryAdminResponse;
import com.jugger.springcommerce.modules.homepage.model.HomepageFeaturedCategory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomepageFeaturedCategoryAdminMapper {

    public HomepageFeaturedCategoryAdminResponse mapToAdminResponse(HomepageFeaturedCategory featuredCategory) {
        return HomepageFeaturedCategoryAdminResponse.builder()
                .id(featuredCategory.getId())
                .caption(featuredCategory.getCaption())
                .imageUrl(featuredCategory.getImageUrl())
                .emphasis(featuredCategory.getEmphasis())
                .isActive(featuredCategory.getIsActive())
                .displayOrder(featuredCategory.getDisplayOrder())
                .categoryId(featuredCategory.getCategory() != null ? featuredCategory.getCategory().getId() : null)
                .categoryName(featuredCategory.getCategory() != null ? featuredCategory.getCategory().getName() : null)
                .build();
    }

    public HomepageFeaturedCategoryAdminResponse mapToAdminResponse(ResultSet rs) throws SQLException {
        return HomepageFeaturedCategoryAdminResponse.builder()
                .id(rs.getLong("id"))
                .caption(rs.getString("caption"))
                .imageUrl(rs.getString("image_url"))
                .emphasis(rs.getString("emphasis"))
                .isActive(rs.getBoolean("is_active"))
                .displayOrder(rs.getInt("display_order"))
                .categoryId((Long) rs.getObject("category_id"))
                .categoryName(rs.getString("category_name"))
                .build();
    }
}
