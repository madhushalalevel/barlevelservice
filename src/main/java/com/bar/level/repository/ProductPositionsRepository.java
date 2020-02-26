package com.bar.level.repository;

import com.bar.level.domain.ProductPositions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductPositions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductPositionsRepository extends JpaRepository<ProductPositions, Long> {

}
