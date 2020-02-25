package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProductImage;
import com.mycompany.myapp.repository.ProductImageRepository;
import com.mycompany.myapp.service.dto.ProductImageDTO;
import com.mycompany.myapp.service.mapper.ProductImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductImage}.
 */
@Service
@Transactional
public class ProductImageService {

    private final Logger log = LoggerFactory.getLogger(ProductImageService.class);

    private final ProductImageRepository productImageRepository;

    private final ProductImageMapper productImageMapper;

    public ProductImageService(ProductImageRepository productImageRepository, ProductImageMapper productImageMapper) {
        this.productImageRepository = productImageRepository;
        this.productImageMapper = productImageMapper;
    }

    /**
     * Save a productImage.
     *
     * @param productImageDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductImageDTO save(ProductImageDTO productImageDTO) {
        log.debug("Request to save ProductImage : {}", productImageDTO);
        ProductImage productImage = productImageMapper.toEntity(productImageDTO);
        productImage = productImageRepository.save(productImage);
        return productImageMapper.toDto(productImage);
    }

    /**
     * Get all the productImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductImages");
        return productImageRepository.findAll(pageable)
            .map(productImageMapper::toDto);
    }


    /**
     * Get one productImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductImageDTO> findOne(Long id) {
        log.debug("Request to get ProductImage : {}", id);
        return productImageRepository.findById(id)
            .map(productImageMapper::toDto);
    }

    /**
     * Delete the productImage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductImage : {}", id);
        productImageRepository.deleteById(id);
    }
}
