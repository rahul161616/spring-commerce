package com.jugger.springcommerce.modules.product.service.impl;

import com.jugger.springcommerce.common.exception.AlreadyExistsException;
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

import java.util.List;

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
        productRepository.save(product);
        if(request.getTagIds()!=null && !request.getTagIds().isEmpty()){
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());

            for(Tag tag:tags){
                ProductTag productTag = ProductTag.builder()
                        .product(product)
                        .tag(tag)
                        .build();
                productTagRepository.save(productTag);
                product.getProductTags().add(productTag);
            }
        }
        return mapper.mapToAdminResponse(product);
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
}
