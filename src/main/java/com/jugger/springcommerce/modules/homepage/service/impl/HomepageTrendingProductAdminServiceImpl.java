package com.jugger.springcommerce.modules.homepage.service.impl;

import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.trendingProduct.HomepageTrendingProductAdminResponse;
import com.jugger.springcommerce.modules.homepage.mapper.HomepageTrendingProductAdminMapper;
import com.jugger.springcommerce.modules.homepage.model.HomepageTrendingProduct;
import com.jugger.springcommerce.modules.homepage.repository.HomepageTrendingProductRepository;
import com.jugger.springcommerce.modules.homepage.service.HomepageTrendingProductAdminService;
import com.jugger.springcommerce.modules.product.model.Product;
import com.jugger.springcommerce.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageTrendingProductAdminServiceImpl implements HomepageTrendingProductAdminService {
    private final HomepageTrendingProductRepository homepageTrendingProductRepository;
    private final ProductRepository productRepository;
    private final HomepageTrendingProductAdminMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public HomepageTrendingProductAdminResponse addHomePageTrendingProduct(HomepageTrendingProductAdminRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (request.getProductId() == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        HomepageTrendingProduct trendingProduct = HomepageTrendingProduct.builder()
                .product(product)
                .label(request.getLabel())
                .isActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE)
                .displayOrder(nextDisplayOrder())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        HomepageTrendingProduct savedTrendingProduct = homepageTrendingProductRepository.save(trendingProduct);
        return mapper.mapToAdminResponse(savedTrendingProduct);
    }

    @Override
    public HomepageTrendingProductAdminResponse getTrendingProductByIdForAdmin(Long id) {
        String sql = """
                SELECT h.id,
                       h.product_id,
                       h.label,
                       h.is_active,
                       h.display_order
                FROM homepage_trending_products h
                WHERE h.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapper.mapToAdminResponse(rs), id);
    }

    @Override
    public List<HomepageTrendingProductAdminResponse> getTrendingProductForAdmin() {
        String sql = """
                SELECT h.id,
                       h.product_id,
                       h.label,
                       h.is_active,
                       h.display_order
                FROM homepage_trending_products h
                ORDER BY h.display_order ASC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapper.mapToAdminResponse(rs));
    }

    @Override
    public HomepageTrendingProductAdminResponse updateTrendingProduct(Long id, HomepageTrendingProductAdminRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Trending product id cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        HomepageTrendingProduct trendingProduct = homepageTrendingProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Homepage trending product not found with id: " + id));

        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));
            trendingProduct.setProduct(product);
        }
        if (request.getLabel() != null) {
            trendingProduct.setLabel(request.getLabel());
        }
        if (request.getIsActive() != null) {
            trendingProduct.setIsActive(request.getIsActive());
        }
        if (request.getDisplayOrder() != null) {
            trendingProduct.setDisplayOrder(request.getDisplayOrder());
        }
        trendingProduct.setUpdatedAt(Instant.now());

        HomepageTrendingProduct updatedTrendingProduct = homepageTrendingProductRepository.save(trendingProduct);
        return mapper.mapToAdminResponse(updatedTrendingProduct);
    }

    @Override
    public void deleteTrendingProduct(Long id) {
        HomepageTrendingProduct trendingProduct = homepageTrendingProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Homepage trending product not found with id: " + id));
        trendingProduct.setIsActive(Boolean.FALSE);
        homepageTrendingProductRepository.save(trendingProduct);
    }

    private Integer nextDisplayOrder() {
        HomepageTrendingProduct latestTrendingProduct = homepageTrendingProductRepository.findTopByOrderByDisplayOrderDesc();
        if (latestTrendingProduct == null || latestTrendingProduct.getDisplayOrder() == null) {
            return 1;
        }
        return latestTrendingProduct.getDisplayOrder() + 1;
    }
}
