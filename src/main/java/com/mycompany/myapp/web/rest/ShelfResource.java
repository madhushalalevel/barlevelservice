package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ShelfService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ShelfDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Shelf}.
 */
@RestController
@RequestMapping("/api")
public class ShelfResource {

    private final Logger log = LoggerFactory.getLogger(ShelfResource.class);

    private static final String ENTITY_NAME = "shelf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShelfService shelfService;

    public ShelfResource(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    /**
     * {@code POST  /shelves} : Create a new shelf.
     *
     * @param shelfDTO the shelfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shelfDTO, or with status {@code 400 (Bad Request)} if the shelf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shelves")
    public ResponseEntity<ShelfDTO> createShelf(@RequestBody ShelfDTO shelfDTO) throws URISyntaxException {
        log.debug("REST request to save Shelf : {}", shelfDTO);
        if (shelfDTO.getId() != null) {
            throw new BadRequestAlertException("A new shelf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShelfDTO result = shelfService.save(shelfDTO);
        return ResponseEntity.created(new URI("/api/shelves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shelves} : Updates an existing shelf.
     *
     * @param shelfDTO the shelfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shelfDTO,
     * or with status {@code 400 (Bad Request)} if the shelfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shelfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shelves")
    public ResponseEntity<ShelfDTO> updateShelf(@RequestBody ShelfDTO shelfDTO) throws URISyntaxException {
        log.debug("REST request to update Shelf : {}", shelfDTO);
        if (shelfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShelfDTO result = shelfService.save(shelfDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shelfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shelves} : get all the shelves.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shelves in body.
     */
    @GetMapping("/shelves")
    public ResponseEntity<List<ShelfDTO>> getAllShelves(Pageable pageable) {
        log.debug("REST request to get a page of Shelves");
        Page<ShelfDTO> page = shelfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shelves/:id} : get the "id" shelf.
     *
     * @param id the id of the shelfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shelfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shelves/{id}")
    public ResponseEntity<ShelfDTO> getShelf(@PathVariable Long id) {
        log.debug("REST request to get Shelf : {}", id);
        Optional<ShelfDTO> shelfDTO = shelfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shelfDTO);
    }

    /**
     * {@code DELETE  /shelves/:id} : delete the "id" shelf.
     *
     * @param id the id of the shelfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shelves/{id}")
    public ResponseEntity<Void> deleteShelf(@PathVariable Long id) {
        log.debug("REST request to delete Shelf : {}", id);
        shelfService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
