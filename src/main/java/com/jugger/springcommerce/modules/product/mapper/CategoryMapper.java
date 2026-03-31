package com.jugger.springcommerce.modules.product.mapper;

import com.jugger.springcommerce.modules.product.dto.admin.CategoryAdminResponse;
import com.jugger.springcommerce.modules.product.model.Category;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryMapper {

    public CategoryAdminResponse mapToCategoryAdminResponse(Category category) {
        CategoryAdminResponse resp = CategoryAdminResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .build();
        if (category.getParent() != null) {
            resp.setParentId(category.getParent().getId());
            resp.setParentName(category.getParent().getName());
        }

        return resp;
    }

    public CategoryAdminResponse mapRowToAdminResponse(ResultSet rs) throws SQLException {
        Object parentIdValue = rs.getObject("parent_id");

        return CategoryAdminResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .slug(rs.getString("slug"))
                .description(rs.getString("description"))
                .isActive(rs.getBoolean("is_active"))
                .parentId(parentIdValue == null ? null : rs.getLong("parent_id"))
                .parentName(rs.getString("parent_name"))
                .build();
    }
    public CategoryAdminResponse mapRowToAdminResponseForOptions(ResultSet rs) throws SQLException {
        return CategoryAdminResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .slug(rs.getString("slug"))
                .build();
    }
}
