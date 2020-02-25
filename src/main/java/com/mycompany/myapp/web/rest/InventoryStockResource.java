package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.InventoryStockService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.InventoryStockDTO;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.InventoryStock}.
 */
@RestController
@RequestMapping("/api")
public class InventoryStockResource {

    private final Logger log = LoggerFactory.getLogger(InventoryStockResource.class);

    private static final String ENTITY_NAME = "inventoryStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryStockService inventoryStockService;

    public InventoryStockResource(InventoryStockService inventoryStockService) {
        this.inventoryStockService = inventoryStockService;
    }

    /**
     * {@code POST  /inventory-stocks} : Create a new inventoryStock.
     *
     * @param inventoryStockDTO the inventoryStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryStockDTO, or with status {@code 400 (Bad Request)} if the inventoryStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-stocks")
    public ResponseEntity<InventoryStockDTO> createInventoryStock(@RequestBody InventoryStockDTO inventoryStockDTO) throws URISyntaxException {
        log.debug("REST request to save InventoryStock : {}", inventoryStockDTO);
        if (inventoryStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventoryStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryStockDTO result = inventoryStockService.save(inventoryStockDTO);
        return ResponseEntity.created(new URI("/api/inventory-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-stocks} : Updates an existing inventoryStock.
     *
     * @param inventoryStockDTO the inventoryStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryStockDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-stocks")
    public ResponseEntity<InventoryStockDTO> updateInventoryStock(@RequestBody InventoryStockDTO inventoryStockDTO) throws URISyntaxException {
        log.debug("REST request to update InventoryStock : {}", inventoryStockDTO);
        if (inventoryStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryStockDTO result = inventoryStockService.save(inventoryStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-stocks} : get all the inventoryStocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryStocks in body.
     */
    @GetMapping("/inventory-stocks")
    public ResponseEntity<List<InventoryStockDTO>> getAllInventoryStocks(Pageable pageable) {
        log.debug("REST request to get a page of InventoryStocks");
        Page<InventoryStockDTO> page = inventoryStockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-stocks/:id} : get the "id" inventoryStock.
     *
     * @param id the id of the inventoryStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-stocks/{id}")
    public ResponseEntity<InventoryStockDTO> getInventoryStock(@PathVariable Long id) {
        log.debug("REST request to get InventoryStock : {}", id);
        Optional<InventoryStockDTO> inventoryStockDTO = inventoryStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryStockDTO);
    }

    /**
     * {@code DELETE  /inventory-stocks/:id} : delete the "id" inventoryStock.
     *
     * @param id the id of the inventoryStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-stocks/{id}")
    public ResponseEntity<Void> deleteInventoryStock(@PathVariable Long id) {
        log.debug("REST request to delete InventoryStock : {}", id);
        inventoryStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
