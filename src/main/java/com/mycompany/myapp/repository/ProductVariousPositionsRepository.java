package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductVariousPositions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductVariousPositions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductVariousPositionsRepository extends JpaRepository<ProductVariousPositions, Long> {

}
