package com.jugger.springcommerce.modules.product.mapper;

import com.jugger.springcommerce.modules.product.dto.ProductPublicResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductPublicMapper {
    public ProductPublicResponse mapToPublicResponse(ResultSet rs) throws SQLException {
       return ProductPublicResponse.builder()
               .id(rs.getLong("id"))
               .name(rs.getString("name"))
               .description(rs.getString("description"))
               .shortDescription(rs.getString("short_description"))
               .slug(rs.getString("slug"))
               .price(rs.getBigDecimal("price"))
               .compareAt(rs.getBigDecimal("compare_at"))
               .imageUrl(rs.getString("image_url"))
               .categoryName(rs.getString("category_name"))
               .tagNames(rs.getString("tag_names"))
               .tagIds(rs.getString("tag_ids"))
               .stockQuantity(rs.getInt("stock_quantity"))
               .build();
    }
}
