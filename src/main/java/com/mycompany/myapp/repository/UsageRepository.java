package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Usage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Usage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsageRepository extends JpaRepository<Usage, Long> {

}
