package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Usage;
import com.mycompany.myapp.repository.UsageRepository;
import com.mycompany.myapp.service.dto.UsageDTO;
import com.mycompany.myapp.service.mapper.UsageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Usage}.
 */
@Service
@Transactional
public class UsageService {

    private final Logger log = LoggerFactory.getLogger(UsageService.class);

    private final UsageRepository usageRepository;

    private final UsageMapper usageMapper;

    public UsageService(UsageRepository usageRepository, UsageMapper usageMapper) {
        this.usageRepository = usageRepository;
        this.usageMapper = usageMapper;
    }

    /**
     * Save a usage.
     *
     * @param usageDTO the entity to save.
     * @return the persisted entity.
     */
    public UsageDTO save(UsageDTO usageDTO) {
        log.debug("Request to save Usage : {}", usageDTO);
        Usage usage = usageMapper.toEntity(usageDTO);
        usage = usageRepository.save(usage);
        return usageMapper.toDto(usage);
    }

    /**
     * Get all the usages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usages");
        return usageRepository.findAll(pageable)
            .map(usageMapper::toDto);
    }

    /**
     * Get one usage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsageDTO> findOne(Long id) {
        log.debug("Request to get Usage : {}", id);
        return usageRepository.findById(id)
            .map(usageMapper::toDto);
    }

    /**
     * Delete the usage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Usage : {}", id);
        usageRepository.deleteById(id);
    }
}
