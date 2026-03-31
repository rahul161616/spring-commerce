package com.jugger.springcommerce.modules.product.repository;

import com.jugger.springcommerce.modules.product.enums.ProductStatus;
import com.jugger.springcommerce.modules.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findBySlugAndStatus(String slug, ProductStatus status);

    boolean existsBySlug(String slug);

//    Page<Product> findAllByStatus(ProductStatus status, Pageable pageable);
}
