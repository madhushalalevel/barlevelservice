package com.bar.level.service;

import com.bar.level.domain.ProductPositions;
import com.bar.level.repository.ProductPositionsRepository;
import com.bar.level.service.dto.ProductPositionsDTO;
import com.bar.level.service.mapper.ProductPositionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductPositions}.
 */
@Service
@Transactional
public class ProductPositionsService {

    private final Logger log = LoggerFactory.getLogger(ProductPositionsService.class);

    private final ProductPositionsRepository productPositionsRepository;

    private final ProductPositionsMapper productPositionsMapper;

    public ProductPositionsService(ProductPositionsRepository productPositionsRepository, ProductPositionsMapper productPositionsMapper) {
        this.productPositionsRepository = productPositionsRepository;
        this.productPositionsMapper = productPositionsMapper;
    }

    /**
     * Save a productPositions.
     *
     * @param productPositionsDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductPositionsDTO save(ProductPositionsDTO productPositionsDTO) {
        log.debug("Request to save ProductPositions : {}", productPositionsDTO);
        ProductPositions productPositions = productPositionsMapper.toEntity(productPositionsDTO);
        productPositions = productPositionsRepository.save(productPositions);
        return productPositionsMapper.toDto(productPositions);
    }

    /**
     * Get all the productPositions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductPositionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductPositions");
        return productPositionsRepository.findAll(pageable)
            .map(productPositionsMapper::toDto);
    }

    /**
     * Get one productPositions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductPositionsDTO> findOne(Long id) {
        log.debug("Request to get ProductPositions : {}", id);
        return productPositionsRepository.findById(id)
            .map(productPositionsMapper::toDto);
    }

    /**
     * Delete the productPositions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductPositions : {}", id);
        productPositionsRepository.deleteById(id);
    }
}
