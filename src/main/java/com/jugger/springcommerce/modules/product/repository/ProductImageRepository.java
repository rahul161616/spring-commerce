package com.jugger.springcommerce.modules.product.repository;

import com.jugger.springcommerce.modules.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findAllByProductIdInOrderByProductIdAscDisplayOrderAsc(Collection<Long> productIds);
}
