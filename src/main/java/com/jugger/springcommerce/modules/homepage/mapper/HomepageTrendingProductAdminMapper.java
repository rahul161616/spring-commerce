package com.jugger.springcommerce.modules.homepage.mapper;

import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductAdminResponse;
import com.jugger.springcommerce.modules.homepage.model.HomepageTrendingProduct;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomepageTrendingProductAdminMapper {

    public HomepageTrendingProductAdminResponse mapToAdminResponse(HomepageTrendingProduct trendingProduct) {
        return HomepageTrendingProductAdminResponse.builder()
                .id(trendingProduct.getId())
                .productId(trendingProduct.getProduct() != null ? trendingProduct.getProduct().getId() : null)
                .label(trendingProduct.getLabel())
                .isActive(trendingProduct.getIsActive())
                .displayOrder(trendingProduct.getDisplayOrder())
                .build();
    }

    public HomepageTrendingProductAdminResponse mapToAdminResponse(ResultSet rs) throws SQLException {
        return HomepageTrendingProductAdminResponse.builder()
                .id(rs.getLong("id"))
                .productId((Long) rs.getObject("product_id"))
                .label(rs.getString("label"))
                .isActive(rs.getBoolean("is_active"))
                .displayOrder(rs.getInt("display_order"))
                .build();
    }
}
