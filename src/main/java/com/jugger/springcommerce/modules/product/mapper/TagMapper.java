package com.jugger.springcommerce.modules.product.mapper;

import com.jugger.springcommerce.modules.product.dto.admin.TagAdminResponse;
import com.jugger.springcommerce.modules.product.model.Tag;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper {
    public TagAdminResponse mapToAdminResponse(Tag tag){
        TagAdminResponse resp = TagAdminResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .description(tag.getDescription())
                .isActive(tag.getIsActive())
                .build();
        return resp;
    }

    public TagAdminResponse mapRowToAdminResponse(ResultSet rs) throws SQLException {
        return TagAdminResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .slug(rs.getString("slug"))
                .description(rs.getString("description"))
                .isActive(rs.getBoolean("is_active"))
                .build();
    }
}
