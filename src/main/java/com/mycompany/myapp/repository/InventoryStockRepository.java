package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.InventoryStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InventoryStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryStockRepository extends JpaRepository<InventoryStock, Long> {

}
