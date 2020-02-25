package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.UsageService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.UsageDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Usage}.
 */
@RestController
@RequestMapping("/api")
public class UsageResource {

    private final Logger log = LoggerFactory.getLogger(UsageResource.class);

    private static final String ENTITY_NAME = "usage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsageService usageService;

    public UsageResource(UsageService usageService) {
        this.usageService = usageService;
    }

    /**
     * {@code POST  /usages} : Create a new usage.
     *
     * @param usageDTO the usageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usageDTO, or with status {@code 400 (Bad Request)} if the usage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usages")
    public ResponseEntity<UsageDTO> createUsage(@RequestBody UsageDTO usageDTO) throws URISyntaxException {
        log.debug("REST request to save Usage : {}", usageDTO);
        if (usageDTO.getId() != null) {
            throw new BadRequestAlertException("A new usage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsageDTO result = usageService.save(usageDTO);
        return ResponseEntity.created(new URI("/api/usages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usages} : Updates an existing usage.
     *
     * @param usageDTO the usageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usageDTO,
     * or with status {@code 400 (Bad Request)} if the usageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usages")
    public ResponseEntity<UsageDTO> updateUsage(@RequestBody UsageDTO usageDTO) throws URISyntaxException {
        log.debug("REST request to update Usage : {}", usageDTO);
        if (usageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsageDTO result = usageService.save(usageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usages} : get all the usages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usages in body.
     */
    @GetMapping("/usages")
    public ResponseEntity<List<UsageDTO>> getAllUsages(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Usages");
        Page<UsageDTO> page = usageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usages/:id} : get the "id" usage.
     *
     * @param id the id of the usageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usages/{id}")
    public ResponseEntity<UsageDTO> getUsage(@PathVariable Long id) {
        log.debug("REST request to get Usage : {}", id);
        Optional<UsageDTO> usageDTO = usageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usageDTO);
    }

    /**
     * {@code DELETE  /usages/:id} : delete the "id" usage.
     *
     * @param id the id of the usageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usages/{id}")
    public ResponseEntity<Void> deleteUsage(@PathVariable Long id) {
        log.debug("REST request to delete Usage : {}", id);
        usageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
