package com.jugger.springcommerce.modules.homepage.mapper;

import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductPublicResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomepageTrendingProductPublicMapper {

    public HomepageTrendingProductPublicResponse mapToPublicResponse(ResultSet rs) throws SQLException {
        return HomepageTrendingProductPublicResponse.builder()
                .id(rs.getLong("id"))
                .family(rs.getString("family"))
                .title(rs.getString("title"))
                .price(rs.getString("price"))
                .compareAt(rs.getString("compare_at"))
                .image(rs.getString("image"))
                .href(rs.getString("href"))
                .build();
    }
}
