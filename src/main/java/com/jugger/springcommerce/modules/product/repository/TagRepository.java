package com.jugger.springcommerce.modules.product.repository;

import com.jugger.springcommerce.modules.product.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findBySlug(String slug);
    List<Tag> findAllBySlugIn(List<String> slugs);
}
