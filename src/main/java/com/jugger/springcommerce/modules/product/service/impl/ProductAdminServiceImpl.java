package com.jugger.springcommerce.modules.product.service.impl;

import com.jugger.springcommerce.common.exception.AlreadyExistsException;
import com.jugger.springcommerce.common.exception.BusinessException;
import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.product.dto.admin.CreateProductRequest;
import com.jugger.springcommerce.modules.product.dto.admin.ProductAdminResponse;
import com.jugger.springcommerce.modules.product.mapper.ProductAdminMapper;
import com.jugger.springcommerce.modules.product.model.Category;
import com.jugger.springcommerce.modules.product.model.Product;
import com.jugger.springcommerce.modules.product.model.ProductTag;
import com.jugger.springcommerce.modules.product.model.Tag;
import com.jugger.springcommerce.modules.product.repository.CategoryRepository;
import com.jugger.springcommerce.modules.product.repository.ProductRepository;
import com.jugger.springcommerce.modules.product.repository.ProductTagRepository;
import com.jugger.springcommerce.modules.product.repository.TagRepository;
import com.jugger.springcommerce.modules.product.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
                       COALESCE(STRING_AGG(t.slug, ',' ORDER BY t.slug), '') AS tags
                FROM products p
                JOIN categories c ON c.id = p.category_id
                LEFT JOIN product_tags pt ON pt.product_id = p.id
                LEFT JOIN tags t ON t.id = pt.tag_id
                GROUP BY p.id, p.name, p.slug, p.price, p.stock_quantity, c.name
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
}
