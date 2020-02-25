package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProductVariousPositions;
import com.mycompany.myapp.repository.ProductVariousPositionsRepository;
import com.mycompany.myapp.service.dto.ProductVariousPositionsDTO;
import com.mycompany.myapp.service.mapper.ProductVariousPositionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductVariousPositions}.
 */
@Service
@Transactional
public class ProductVariousPositionsService {

    private final Logger log = LoggerFactory.getLogger(ProductVariousPositionsService.class);

    private final ProductVariousPositionsRepository productVariousPositionsRepository;

    private final ProductVariousPositionsMapper productVariousPositionsMapper;

    public ProductVariousPositionsService(ProductVariousPositionsRepository productVariousPositionsRepository, ProductVariousPositionsMapper productVariousPositionsMapper) {
        this.productVariousPositionsRepository = productVariousPositionsRepository;
        this.productVariousPositionsMapper = productVariousPositionsMapper;
    }

    /**
     * Save a productVariousPositions.
     *
     * @param productVariousPositionsDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductVariousPositionsDTO save(ProductVariousPositionsDTO productVariousPositionsDTO) {
        log.debug("Request to save ProductVariousPositions : {}", productVariousPositionsDTO);
        ProductVariousPositions productVariousPositions = productVariousPositionsMapper.toEntity(productVariousPositionsDTO);
        productVariousPositions = productVariousPositionsRepository.save(productVariousPositions);
        return productVariousPositionsMapper.toDto(productVariousPositions);
    }

    /**
     * Get all the productVariousPositions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductVariousPositionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductVariousPositions");
        return productVariousPositionsRepository.findAll(pageable)
            .map(productVariousPositionsMapper::toDto);
    }


    /**
     * Get one productVariousPositions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductVariousPositionsDTO> findOne(Long id) {
        log.debug("Request to get ProductVariousPositions : {}", id);
        return productVariousPositionsRepository.findById(id)
            .map(productVariousPositionsMapper::toDto);
    }

    /**
     * Delete the productVariousPositions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductVariousPositions : {}", id);
        productVariousPositionsRepository.deleteById(id);
    }
}
