package com.jugger.springcommerce.modules.product.mapper;

import com.jugger.springcommerce.modules.product.dto.admin.ProductAdminResponse;
import com.jugger.springcommerce.modules.product.model.Product;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductAdminMapper {
    public ProductAdminResponse mapToAdminResponse(Product product){
        ProductAdminResponse resp = ProductAdminResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory().getName())
                .build();
        List<String> tags = product.getProductTags().stream().map(pt -> pt.getTag().getSlug())
                .toList();
        resp.setTags(tags);
        return resp;
    }

    public ProductAdminResponse mapRowToAdminResponse(ResultSet rs) throws SQLException {
        String tagsValue = rs.getString("tags");
        List<String> tags = tagsValue == null || tagsValue.isBlank()
                ? List.of()
                : Arrays.stream(tagsValue.split(","))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .toList();

        return ProductAdminResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .slug(rs.getString("slug"))
                .price(rs.getBigDecimal("price"))
                .stockQuantity(rs.getInt("stock_quantity"))
                .categoryName(rs.getString("category_name"))
                .tags(tags)
                .build();
    }
}
