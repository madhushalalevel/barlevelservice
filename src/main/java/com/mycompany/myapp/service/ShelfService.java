package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Shelf;
import com.mycompany.myapp.repository.ShelfRepository;
import com.mycompany.myapp.service.dto.ShelfDTO;
import com.mycompany.myapp.service.mapper.ShelfMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Shelf}.
 */
@Service
@Transactional
public class ShelfService {

    private final Logger log = LoggerFactory.getLogger(ShelfService.class);

    private final ShelfRepository shelfRepository;

    private final ShelfMapper shelfMapper;

    public ShelfService(ShelfRepository shelfRepository, ShelfMapper shelfMapper) {
        this.shelfRepository = shelfRepository;
        this.shelfMapper = shelfMapper;
    }

    /**
     * Save a shelf.
     *
     * @param shelfDTO the entity to save.
     * @return the persisted entity.
     */
    public ShelfDTO save(ShelfDTO shelfDTO) {
        log.debug("Request to save Shelf : {}", shelfDTO);
        Shelf shelf = shelfMapper.toEntity(shelfDTO);
        shelf = shelfRepository.save(shelf);
        return shelfMapper.toDto(shelf);
    }

    /**
     * Get all the shelves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ShelfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shelves");
        return shelfRepository.findAll(pageable)
            .map(shelfMapper::toDto);
    }


    /**
     * Get one shelf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShelfDTO> findOne(Long id) {
        log.debug("Request to get Shelf : {}", id);
        return shelfRepository.findById(id)
            .map(shelfMapper::toDto);
    }

    /**
     * Delete the shelf by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Shelf : {}", id);
        shelfRepository.deleteById(id);
    }
}
