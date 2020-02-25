package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Shelf;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Shelf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {

}
