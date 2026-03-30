package com.jugger.springcommerce.modules.homepage.mapper;

import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalAdminResponse;
import com.jugger.springcommerce.modules.homepage.model.HomepageNewArrivalRule;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomepageNewArrivalAdminMapper {

    public HomepageNewArrivalAdminResponse mapToAdminResponse(HomepageNewArrivalRule rule) {
        return HomepageNewArrivalAdminResponse.builder()
                .id(rule.getId())
                .limitCount(rule.getLimitCount())
                .categoryId(rule.getCategory() != null ? rule.getCategory().getId() : null)
                .categoryName(rule.getCategory() != null ? rule.getCategory().getName() : null)
                .tagId(rule.getTag() != null ? rule.getTag().getId() : null)
                .tagName(rule.getTag() != null ? rule.getTag().getName() : null)
                .onlyActive(rule.getOnlyActive())
                .isActive(rule.getIsActive())
                .build();
    }

    public HomepageNewArrivalAdminResponse mapToAdminResponse(ResultSet rs) throws SQLException {
        return HomepageNewArrivalAdminResponse.builder()
                .id(rs.getLong("id"))
                .limitCount(rs.getInt("limit_count"))
                .categoryId((Long) rs.getObject("category_id"))
                .categoryName(rs.getString("category_name"))
                .tagId((Long) rs.getObject("tag_id"))
                .tagName(rs.getString("tag_name"))
                .onlyActive(rs.getBoolean("only_active"))
                .isActive(rs.getBoolean("is_active"))
                .build();
    }
}
