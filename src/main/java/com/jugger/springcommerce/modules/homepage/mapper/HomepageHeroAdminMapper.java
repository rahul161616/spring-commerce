package com.jugger.springcommerce.modules.homepage.mapper;

import com.jugger.springcommerce.modules.homepage.dto.hero.HomepageHeroAdminResponse;
import com.jugger.springcommerce.modules.homepage.model.HomepageHero;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomepageHeroAdminMapper {

  public HomepageHeroAdminResponse mapHeroToAdminResponse(HomepageHero hero){
      return HomepageHeroAdminResponse.builder()
              .id(hero.getId())
              .eyebrow(hero.getEyebrow())
              .title(hero.getTitle())
              .supportingText(hero.getSupportingText())
              .ctaLabel(hero.getCtaLabel())
              .ctaUrl(hero.getCtaUrl())
              .imageUrl(hero.getImageUrl())
              .displayOrder(hero.getDisplayOrder())
              .isActive(hero.getIsActive())
              .linkProductId(hero.getProduct() != null ? hero.getProduct().getId() : null)
              .linkCategoryId(hero.getCategory() != null ? hero.getCategory().getId() : null)
              .build();
  }

  public HomepageHeroAdminResponse mapHeroToAdminResponse(ResultSet rs) throws SQLException {
      return HomepageHeroAdminResponse.builder()
              .id(rs.getLong("id"))
              .eyebrow(rs.getString("eyebrow"))
              .title(rs.getString("title"))
              .supportingText(rs.getString("supporting_text"))
              .ctaLabel(rs.getString("cta_label"))
              .ctaUrl(rs.getString("cta_href"))
              .imageUrl(rs.getString("image_url"))
              .displayOrder(rs.getInt("display_order"))
              .isActive(rs.getBoolean("is_active"))
              .linkProductId((Long) rs.getObject("linked_product_id"))
              .linkCategoryId((Long) rs.getObject("linked_category_id"))
              .build();
  }

}
