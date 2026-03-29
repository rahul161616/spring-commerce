package com.jugger.springcommerce.modules.product.mapper;

import com.jugger.springcommerce.modules.product.dto.admin.ProductAdminResponse;
import com.jugger.springcommerce.modules.product.dto.admin.ProductImageAdminResponse;
import com.jugger.springcommerce.modules.product.dto.admin.UpdateStatusResponseForAdmin;
import com.jugger.springcommerce.modules.product.enums.ProductStatus;
import com.jugger.springcommerce.modules.product.model.Product;
import com.jugger.springcommerce.modules.product.model.ProductImage;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class ProductAdminMapper {
    private static final String IMAGE_RECORD_SEPARATOR = "\u001E";
    private static final String IMAGE_FIELD_SEPARATOR = "\u001F";

    public ProductAdminResponse mapToAdminResponse(Product product){
        ProductAdminResponse resp = ProductAdminResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .isFeatured(product.getIsFeatured())
                .status(product.getStatus())
                .build();
        List<String> tags = product.getProductTags().stream().map(pt -> pt.getTag().getSlug())
                .toList();
        resp.setTags(tags);
        List<ProductImageAdminResponse> images = (product.getImages() == null) ? List.of() :
                product.getImages().stream()
                        .sorted(Comparator.comparing(ProductImage::getDisplayOrder))
                        .map(this::mapImageToAdminResponse)
                        .toList();
        resp.setImages(images);
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

        String imagesValue = rs.getString("images_data");
        List<ProductImageAdminResponse> images = parseImages(imagesValue);

        return ProductAdminResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .slug(rs.getString("slug"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .stockQuantity(rs.getInt("stock_quantity"))
                .categoryName(rs.getString("category_name"))
                .isFeatured(rs.getBoolean("is_featured"))
                .status(parseStatus(rs.getString("status")))
                .tags(tags)
                .images(images)
                .build();
    }

    public ProductImageAdminResponse mapImageToAdminResponse(ProductImage image) {
        return ProductImageAdminResponse.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .isPrimary(image.getIsPrimary())
                .displayOrder(image.getDisplayOrder())
                .build();
    }

    private List<ProductImageAdminResponse> parseImages(String imagesValue) {
        if (imagesValue == null || imagesValue.isBlank()) {
            return List.of();
        }

        return Arrays.stream(imagesValue.split(IMAGE_RECORD_SEPARATOR, -1))
                .filter(value -> !value.isBlank())
                .map(record -> {
                    String[] parts = record.split(IMAGE_FIELD_SEPARATOR, -1);
                    return ProductImageAdminResponse.builder()
                            .id(parts.length > 0 && !parts[0].isBlank() ? Long.valueOf(parts[0]) : null)
                            .imageUrl(parts.length > 1 ? parts[1] : null)
                            .isPrimary(parts.length > 2 && !parts[2].isBlank() ? Boolean.valueOf(parts[2]) : null)
                            .displayOrder(parts.length > 3 && !parts[3].isBlank() ? Integer.valueOf(parts[3]) : null)
                            .build();
                })
                .toList();
    }
    public ProductAdminResponse mapProductByIdRowToAdminResponse(ResultSet rs) throws SQLException {
        String tagsValue = rs.getString("tags");
        List<String> tags = tagsValue == null || tagsValue.isBlank()
                ? List.of()
                : Arrays.stream(tagsValue.split(","))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .toList();

        String imagesValue = rs.getString("images_data");
        List<ProductImageAdminResponse> images = parseImages(imagesValue);

        return ProductAdminResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .slug(rs.getString("slug"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .stockQuantity(rs.getInt("stock_quantity"))
                .categoryName(rs.getString("category_name"))
                .isFeatured(rs.getBoolean("is_featured"))
                .status(parseStatus(rs.getString("status")))
                .tags(tags)
                .images(images)
                .build();

    }

    private ProductStatus parseStatus(String value) {
        return value == null || value.isBlank() ? null : ProductStatus.valueOf(value);
    }
    public UpdateStatusResponseForAdmin mapUpdateStatusForProductByAdmin(Product product){
        return UpdateStatusResponseForAdmin.builder()
                .message("Product's status changed successfully to " + product.getStatus() +" ." )
                .status(product.getStatus())
                .build();
    }
    
}
