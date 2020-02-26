package com.bar.level.repository;

import com.bar.level.domain.ProductVariousPositions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductVariousPositions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductVariousPositionsRepository extends JpaRepository<ProductVariousPositions, Long> {

}
