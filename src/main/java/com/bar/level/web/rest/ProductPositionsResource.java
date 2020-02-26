package com.bar.level.web.rest;

import com.bar.level.service.ProductPositionsService;
import com.bar.level.web.rest.errors.BadRequestAlertException;
import com.bar.level.service.dto.ProductPositionsDTO;

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
 * REST controller for managing {@link com.bar.level.domain.ProductPositions}.
 */
@RestController
@RequestMapping("/api")
public class ProductPositionsResource {

    private final Logger log = LoggerFactory.getLogger(ProductPositionsResource.class);

    private static final String ENTITY_NAME = "productPositions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductPositionsService productPositionsService;

    public ProductPositionsResource(ProductPositionsService productPositionsService) {
        this.productPositionsService = productPositionsService;
    }

    /**
     * {@code POST  /product-positions} : Create a new productPositions.
     *
     * @param productPositionsDTO the productPositionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productPositionsDTO, or with status {@code 400 (Bad Request)} if the productPositions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-positions")
    public ResponseEntity<ProductPositionsDTO> createProductPositions(@RequestBody ProductPositionsDTO productPositionsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductPositions : {}", productPositionsDTO);
        if (productPositionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productPositions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductPositionsDTO result = productPositionsService.save(productPositionsDTO);
        return ResponseEntity.created(new URI("/api/product-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-positions} : Updates an existing productPositions.
     *
     * @param productPositionsDTO the productPositionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPositionsDTO,
     * or with status {@code 400 (Bad Request)} if the productPositionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productPositionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-positions")
    public ResponseEntity<ProductPositionsDTO> updateProductPositions(@RequestBody ProductPositionsDTO productPositionsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductPositions : {}", productPositionsDTO);
        if (productPositionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductPositionsDTO result = productPositionsService.save(productPositionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productPositionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-positions} : get all the productPositions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productPositions in body.
     */
    @GetMapping("/product-positions")
    public ResponseEntity<List<ProductPositionsDTO>> getAllProductPositions(Pageable pageable) {
        log.debug("REST request to get a page of ProductPositions");
        Page<ProductPositionsDTO> page = productPositionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-positions/:id} : get the "id" productPositions.
     *
     * @param id the id of the productPositionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productPositionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-positions/{id}")
    public ResponseEntity<ProductPositionsDTO> getProductPositions(@PathVariable Long id) {
        log.debug("REST request to get ProductPositions : {}", id);
        Optional<ProductPositionsDTO> productPositionsDTO = productPositionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productPositionsDTO);
    }

    /**
     * {@code DELETE  /product-positions/:id} : delete the "id" productPositions.
     *
     * @param id the id of the productPositionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-positions/{id}")
    public ResponseEntity<Void> deleteProductPositions(@PathVariable Long id) {
        log.debug("REST request to delete ProductPositions : {}", id);
        productPositionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
