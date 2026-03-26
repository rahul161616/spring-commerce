package com.jugger.springcommerce.modules.product.repository;

import com.jugger.springcommerce.modules.product.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository <ProductTag,Long> {
}
