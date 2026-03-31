package com.jugger.springcommerce.modules.product.repository;

import com.jugger.springcommerce.modules.product.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findBySlug(String slug);
    Optional<Category> findByIdAndIsActiveTrue(Long id);
}
