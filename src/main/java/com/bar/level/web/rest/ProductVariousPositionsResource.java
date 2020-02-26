package com.bar.level.web.rest;

import com.bar.level.service.ProductVariousPositionsService;
import com.bar.level.web.rest.errors.BadRequestAlertException;
import com.bar.level.service.dto.ProductVariousPositionsDTO;

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
 * REST controller for managing {@link com.bar.level.domain.ProductVariousPositions}.
 */
@RestController
@RequestMapping("/api")
public class ProductVariousPositionsResource {

    private final Logger log = LoggerFactory.getLogger(ProductVariousPositionsResource.class);

    private static final String ENTITY_NAME = "productVariousPositions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductVariousPositionsService productVariousPositionsService;

    public ProductVariousPositionsResource(ProductVariousPositionsService productVariousPositionsService) {
        this.productVariousPositionsService = productVariousPositionsService;
    }

    /**
     * {@code POST  /product-various-positions} : Create a new productVariousPositions.
     *
     * @param productVariousPositionsDTO the productVariousPositionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productVariousPositionsDTO, or with status {@code 400 (Bad Request)} if the productVariousPositions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-various-positions")
    public ResponseEntity<ProductVariousPositionsDTO> createProductVariousPositions(@RequestBody ProductVariousPositionsDTO productVariousPositionsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductVariousPositions : {}", productVariousPositionsDTO);
        if (productVariousPositionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productVariousPositions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductVariousPositionsDTO result = productVariousPositionsService.save(productVariousPositionsDTO);
        return ResponseEntity.created(new URI("/api/product-various-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-various-positions} : Updates an existing productVariousPositions.
     *
     * @param productVariousPositionsDTO the productVariousPositionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productVariousPositionsDTO,
     * or with status {@code 400 (Bad Request)} if the productVariousPositionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productVariousPositionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-various-positions")
    public ResponseEntity<ProductVariousPositionsDTO> updateProductVariousPositions(@RequestBody ProductVariousPositionsDTO productVariousPositionsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductVariousPositions : {}", productVariousPositionsDTO);
        if (productVariousPositionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductVariousPositionsDTO result = productVariousPositionsService.save(productVariousPositionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productVariousPositionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-various-positions} : get all the productVariousPositions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productVariousPositions in body.
     */
    @GetMapping("/product-various-positions")
    public ResponseEntity<List<ProductVariousPositionsDTO>> getAllProductVariousPositions(Pageable pageable) {
        log.debug("REST request to get a page of ProductVariousPositions");
        Page<ProductVariousPositionsDTO> page = productVariousPositionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-various-positions/:id} : get the "id" productVariousPositions.
     *
     * @param id the id of the productVariousPositionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productVariousPositionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-various-positions/{id}")
    public ResponseEntity<ProductVariousPositionsDTO> getProductVariousPositions(@PathVariable Long id) {
        log.debug("REST request to get ProductVariousPositions : {}", id);
        Optional<ProductVariousPositionsDTO> productVariousPositionsDTO = productVariousPositionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productVariousPositionsDTO);
    }

    /**
     * {@code DELETE  /product-various-positions/:id} : delete the "id" productVariousPositions.
     *
     * @param id the id of the productVariousPositionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-various-positions/{id}")
    public ResponseEntity<Void> deleteProductVariousPositions(@PathVariable Long id) {
        log.debug("REST request to delete ProductVariousPositions : {}", id);
        productVariousPositionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
