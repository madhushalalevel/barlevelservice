package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BarLevelServiceApp;
import com.mycompany.myapp.domain.Shelf;
import com.mycompany.myapp.repository.ShelfRepository;
import com.mycompany.myapp.service.ShelfService;
import com.mycompany.myapp.service.dto.ShelfDTO;
import com.mycompany.myapp.service.mapper.ShelfMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ShelfResource} REST controller.
 */
@SpringBootTest(classes = BarLevelServiceApp.class)
public class ShelfResourceIT {

    private static final Integer DEFAULT_SHELF_ID = 1;
    private static final Integer UPDATED_SHELF_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private ShelfMapper shelfMapper;

    @Autowired
    private ShelfService shelfService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restShelfMockMvc;

    private Shelf shelf;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShelfResource shelfResource = new ShelfResource(shelfService);
        this.restShelfMockMvc = MockMvcBuilders.standaloneSetup(shelfResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shelf createEntity(EntityManager em) {
        Shelf shelf = new Shelf()
            .shelfID(DEFAULT_SHELF_ID)
            .name(DEFAULT_NAME)
            .discription(DEFAULT_DISCRIPTION)
            .tenantId(DEFAULT_TENANT_ID);
        return shelf;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shelf createUpdatedEntity(EntityManager em) {
        Shelf shelf = new Shelf()
            .shelfID(UPDATED_SHELF_ID)
            .name(UPDATED_NAME)
            .discription(UPDATED_DISCRIPTION)
            .tenantId(UPDATED_TENANT_ID);
        return shelf;
    }

    @BeforeEach
    public void initTest() {
        shelf = createEntity(em);
    }

    @Test
    @Transactional
    public void createShelf() throws Exception {
        int databaseSizeBeforeCreate = shelfRepository.findAll().size();

        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);
        restShelfMockMvc.perform(post("/api/shelves")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelfDTO)))
            .andExpect(status().isCreated());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeCreate + 1);
        Shelf testShelf = shelfList.get(shelfList.size() - 1);
        assertThat(testShelf.getShelfID()).isEqualTo(DEFAULT_SHELF_ID);
        assertThat(testShelf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShelf.getDiscription()).isEqualTo(DEFAULT_DISCRIPTION);
        assertThat(testShelf.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createShelfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shelfRepository.findAll().size();

        // Create the Shelf with an existing ID
        shelf.setId(1L);
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShelfMockMvc.perform(post("/api/shelves")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShelves() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        // Get all the shelfList
        restShelfMockMvc.perform(get("/api/shelves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shelf.getId().intValue())))
            .andExpect(jsonPath("$.[*].shelfID").value(hasItem(DEFAULT_SHELF_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        // Get the shelf
        restShelfMockMvc.perform(get("/api/shelves/{id}", shelf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shelf.getId().intValue()))
            .andExpect(jsonPath("$.shelfID").value(DEFAULT_SHELF_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.discription").value(DEFAULT_DISCRIPTION))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }

    @Test
    @Transactional
    public void getNonExistingShelf() throws Exception {
        // Get the shelf
        restShelfMockMvc.perform(get("/api/shelves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();

        // Update the shelf
        Shelf updatedShelf = shelfRepository.findById(shelf.getId()).get();
        // Disconnect from session so that the updates on updatedShelf are not directly saved in db
        em.detach(updatedShelf);
        updatedShelf
            .shelfID(UPDATED_SHELF_ID)
            .name(UPDATED_NAME)
            .discription(UPDATED_DISCRIPTION)
            .tenantId(UPDATED_TENANT_ID);
        ShelfDTO shelfDTO = shelfMapper.toDto(updatedShelf);

        restShelfMockMvc.perform(put("/api/shelves")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelfDTO)))
            .andExpect(status().isOk());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
        Shelf testShelf = shelfList.get(shelfList.size() - 1);
        assertThat(testShelf.getShelfID()).isEqualTo(UPDATED_SHELF_ID);
        assertThat(testShelf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShelf.getDiscription()).isEqualTo(UPDATED_DISCRIPTION);
        assertThat(testShelf.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingShelf() throws Exception {
        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();

        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShelfMockMvc.perform(put("/api/shelves")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        int databaseSizeBeforeDelete = shelfRepository.findAll().size();

        // Delete the shelf
        restShelfMockMvc.perform(delete("/api/shelves/{id}", shelf.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
