package com.bar.level.repository;

import com.bar.level.domain.ProductImage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
