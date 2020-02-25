package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.InventoryStock;
import com.mycompany.myapp.repository.InventoryStockRepository;
import com.mycompany.myapp.service.dto.InventoryStockDTO;
import com.mycompany.myapp.service.mapper.InventoryStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InventoryStock}.
 */
@Service
@Transactional
public class InventoryStockService {

    private final Logger log = LoggerFactory.getLogger(InventoryStockService.class);

    private final InventoryStockRepository inventoryStockRepository;

    private final InventoryStockMapper inventoryStockMapper;

    public InventoryStockService(InventoryStockRepository inventoryStockRepository, InventoryStockMapper inventoryStockMapper) {
        this.inventoryStockRepository = inventoryStockRepository;
        this.inventoryStockMapper = inventoryStockMapper;
    }

    /**
     * Save a inventoryStock.
     *
     * @param inventoryStockDTO the entity to save.
     * @return the persisted entity.
     */
    public InventoryStockDTO save(InventoryStockDTO inventoryStockDTO) {
        log.debug("Request to save InventoryStock : {}", inventoryStockDTO);
        InventoryStock inventoryStock = inventoryStockMapper.toEntity(inventoryStockDTO);
        inventoryStock = inventoryStockRepository.save(inventoryStock);
        return inventoryStockMapper.toDto(inventoryStock);
    }

    /**
     * Get all the inventoryStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryStockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryStocks");
        return inventoryStockRepository.findAll(pageable)
            .map(inventoryStockMapper::toDto);
    }

    /**
     * Get one inventoryStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoryStockDTO> findOne(Long id) {
        log.debug("Request to get InventoryStock : {}", id);
        return inventoryStockRepository.findById(id)
            .map(inventoryStockMapper::toDto);
    }

    /**
     * Delete the inventoryStock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryStock : {}", id);
        inventoryStockRepository.deleteById(id);
    }
}
