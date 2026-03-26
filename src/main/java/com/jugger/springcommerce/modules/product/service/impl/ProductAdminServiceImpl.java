package com.jugger.springcommerce.modules.product.service.impl;

import com.jugger.springcommerce.common.exception.AlreadyExistsException;
import com.jugger.springcommerce.common.exception.BusinessException;
import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.product.dto.admin.CreateProductRequest;
import com.jugger.springcommerce.modules.product.dto.admin.ProductAdminResponse;
import com.jugger.springcommerce.modules.product.dto.admin.ProductImageAdminRequest;
import com.jugger.springcommerce.modules.product.mapper.ProductAdminMapper;
import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.model.Product;
import com.jugger.springcommerce.modules.product.model.ProductImage;
import com.jugger.springcommerce.modules.product.model.ProductTag;
import com.jugger.springcommerce.modules.product.model.Tag;
import com.jugger.springcommerce.modules.product.repository.CategoryRepository;
import com.jugger.springcommerce.modules.product.repository.ProductImageRepository;
import com.jugger.springcommerce.modules.product.repository.ProductRepository;
import com.jugger.springcommerce.modules.product.repository.ProductTagRepository;
import com.jugger.springcommerce.modules.product.repository.TagRepository;
import com.jugger.springcommerce.modules.product.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductAdminServiceImpl implements ProductAdminService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductAdminMapper mapper;
    private final JdbcTemplate jdbcTemplate;
    private final ProductImageRepository productImageRepository;

    @Transactional
    public ProductAdminResponse createProduct(CreateProductRequest request) {
        String slug = request.getName().toLowerCase().replaceAll(" ", "-");
        if (productRepository.existsBySlug(slug)) {
            throw new AlreadyExistsException("Product with same slug already exists.");
        }

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found")
        );

        Product product = Product.builder()
                .name(request.getName())
                .slug(slug)
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(category)
                .build();
        Product saved = productRepository.save(product);
        attachTags(saved, request.getTagIds());
        attachImages(saved, request.getImages());
        return mapper.mapToAdminResponse(saved);
    }

    public ProductAdminResponse getProductById(Long id){
        return null;
    }

    @Override
    public List<ProductAdminResponse> getAllProductsForAdmin() {
        String sql = """
                SELECT p.id,
                       p.name,
                       p.slug,
                       p.price,
                       p.stock_quantity,
                       c.name AS category_name,
                       COALESCE(tag_data.tags, '') AS tags,
                       COALESCE(image_data.images_data, '') AS images_data
                FROM products p
                JOIN categories c ON c.id = p.category_id
                LEFT JOIN LATERAL (
                    SELECT STRING_AGG(t.slug, ',' ORDER BY t.slug) AS tags
                    FROM product_tags pt
                    JOIN tags t ON t.id = pt.tag_id
                    WHERE pt.product_id = p.id
                ) tag_data ON true
                LEFT JOIN LATERAL (
                    SELECT STRING_AGG(
                               CONCAT(pi.id, CHR(31), pi.image_url, CHR(31), pi.is_primary, CHR(31), pi.display_order),
                               CHR(30) ORDER BY pi.display_order
                           ) AS images_data
                    FROM product_images pi
                    WHERE pi.product_id = p.id
                ) image_data ON true
                ORDER BY p.id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapper.mapRowToAdminResponse(rs));
    }

    private void attachTags(Product product, List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }

        Set<Long> uniqueTagIds = new HashSet<>(tagIds);

        List<Tag> tags = tagRepository.findAllByIdIn(uniqueTagIds);

        if (tags.size() != uniqueTagIds.size()) {
            throw new BusinessException("One or more tags do not exist");
        }

        List<Long> inactiveTagIds = tags.stream()
                .filter(tag -> Boolean.FALSE.equals(tag.getIsActive()))
                .map(Tag::getId)
                .toList();

        if (!inactiveTagIds.isEmpty()) {
            throw new BusinessException("Inactive tags cannot be assigned to product");
        }

        for (Tag tag : tags) {
            ProductTag productTag = ProductTag.builder()
                    .product(product)
                    .tag(tag)
                    .build();
            productTagRepository.save(productTag);
            product.getProductTags().add(productTag);
        }
    }

    private void attachImages(Product product, List<ProductImageAdminRequest> images) {
        if (images == null || images.isEmpty()) {
            return;
        }

        long primaryCount = images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsPrimary()))
                .count();

        if (primaryCount > 1) {
            throw new BusinessException("Only one product image can be primary");
        }

        Set<String> uniqueUrls = new HashSet<>();

        for (int i = 0; i < images.size(); i++) {
            ProductImageAdminRequest request = images.get(i);

            String imageUrl = request.getUrl() != null ? request.getUrl().trim() : null;
            if (imageUrl == null || imageUrl.isBlank()) {
                throw new BusinessException("Image URL is required");
            }

            if (!uniqueUrls.add(imageUrl)) {
                throw new BusinessException("Duplicate image URLs are not allowed");
            }

            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .imageUrl(imageUrl)
                    .isPrimary(Boolean.TRUE.equals(request.getIsPrimary()))
                    .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : i)
                    .build();
            productImageRepository.save(productImage);
            product.getImages().add(productImage);
        }
    }
}

