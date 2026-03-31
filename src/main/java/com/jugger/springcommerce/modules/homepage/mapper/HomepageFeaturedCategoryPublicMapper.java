package com.jugger.springcommerce.modules.homepage.mapper;

import com.jugger.springcommerce.modules.homepage.dto.featuredCategory.HomepageFeaturedCategoryPublicResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomepageFeaturedCategoryPublicMapper {

    public HomepageFeaturedCategoryPublicResponse mapToPublicResponse(ResultSet rs) throws SQLException {
        return HomepageFeaturedCategoryPublicResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .caption(rs.getString("caption"))
                .href(rs.getString("href"))
                .imageUrl(rs.getString("image_url"))
                .emphasis(rs.getString("emphasis"))
                .build();
    }
}
