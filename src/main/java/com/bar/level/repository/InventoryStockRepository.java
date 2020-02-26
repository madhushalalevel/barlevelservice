package com.bar.level.repository;

import com.bar.level.domain.InventoryStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InventoryStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryStockRepository extends JpaRepository<InventoryStock, Long> {

}
