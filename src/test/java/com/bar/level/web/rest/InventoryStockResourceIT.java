package com.bar.level.web.rest;

import com.bar.level.BarlevelserviceApp;
import com.bar.level.domain.InventoryStock;
import com.bar.level.repository.InventoryStockRepository;
import com.bar.level.service.InventoryStockService;
import com.bar.level.service.dto.InventoryStockDTO;
import com.bar.level.service.mapper.InventoryStockMapper;
import com.bar.level.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.bar.level.web.rest.TestUtil.sameInstant;
import static com.bar.level.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InventoryStockResource} REST controller.
 */
@SpringBootTest(classes = BarlevelserviceApp.class)
public class InventoryStockResourceIT {

    private static final Integer DEFAULT_STOCK_COUNT = 1;
    private static final Integer UPDATED_STOCK_COUNT = 2;

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private InventoryStockRepository inventoryStockRepository;

    @Autowired
    private InventoryStockMapper inventoryStockMapper;

    @Autowired
    private InventoryStockService inventoryStockService;

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

    private MockMvc restInventoryStockMockMvc;

    private InventoryStock inventoryStock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryStockResource inventoryStockResource = new InventoryStockResource(inventoryStockService);
        this.restInventoryStockMockMvc = MockMvcBuilders.standaloneSetup(inventoryStockResource)
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
    public static InventoryStock createEntity(EntityManager em) {
        InventoryStock inventoryStock = new InventoryStock()
            .stockCount(DEFAULT_STOCK_COUNT)
            .datetime(DEFAULT_DATETIME);
        return inventoryStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryStock createUpdatedEntity(EntityManager em) {
        InventoryStock inventoryStock = new InventoryStock()
            .stockCount(UPDATED_STOCK_COUNT)
            .datetime(UPDATED_DATETIME);
        return inventoryStock;
    }

    @BeforeEach
    public void initTest() {
        inventoryStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryStock() throws Exception {
        int databaseSizeBeforeCreate = inventoryStockRepository.findAll().size();

        // Create the InventoryStock
        InventoryStockDTO inventoryStockDTO = inventoryStockMapper.toDto(inventoryStock);
        restInventoryStockMockMvc.perform(post("/api/inventory-stocks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryStockDTO)))
            .andExpect(status().isCreated());

        // Validate the InventoryStock in the database
        List<InventoryStock> inventoryStockList = inventoryStockRepository.findAll();
        assertThat(inventoryStockList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryStock testInventoryStock = inventoryStockList.get(inventoryStockList.size() - 1);
        assertThat(testInventoryStock.getStockCount()).isEqualTo(DEFAULT_STOCK_COUNT);
        assertThat(testInventoryStock.getDatetime()).isEqualTo(DEFAULT_DATETIME);
    }

    @Test
    @Transactional
    public void createInventoryStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryStockRepository.findAll().size();

        // Create the InventoryStock with an existing ID
        inventoryStock.setId(1L);
        InventoryStockDTO inventoryStockDTO = inventoryStockMapper.toDto(inventoryStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryStockMockMvc.perform(post("/api/inventory-stocks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryStock in the database
        List<InventoryStock> inventoryStockList = inventoryStockRepository.findAll();
        assertThat(inventoryStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryStocks() throws Exception {
        // Initialize the database
        inventoryStockRepository.saveAndFlush(inventoryStock);

        // Get all the inventoryStockList
        restInventoryStockMockMvc.perform(get("/api/inventory-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockCount").value(hasItem(DEFAULT_STOCK_COUNT)))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(sameInstant(DEFAULT_DATETIME))));
    }
    
    @Test
    @Transactional
    public void getInventoryStock() throws Exception {
        // Initialize the database
        inventoryStockRepository.saveAndFlush(inventoryStock);

        // Get the inventoryStock
        restInventoryStockMockMvc.perform(get("/api/inventory-stocks/{id}", inventoryStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryStock.getId().intValue()))
            .andExpect(jsonPath("$.stockCount").value(DEFAULT_STOCK_COUNT))
            .andExpect(jsonPath("$.datetime").value(sameInstant(DEFAULT_DATETIME)));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryStock() throws Exception {
        // Get the inventoryStock
        restInventoryStockMockMvc.perform(get("/api/inventory-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryStock() throws Exception {
        // Initialize the database
        inventoryStockRepository.saveAndFlush(inventoryStock);

        int databaseSizeBeforeUpdate = inventoryStockRepository.findAll().size();

        // Update the inventoryStock
        InventoryStock updatedInventoryStock = inventoryStockRepository.findById(inventoryStock.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryStock are not directly saved in db
        em.detach(updatedInventoryStock);
        updatedInventoryStock
            .stockCount(UPDATED_STOCK_COUNT)
            .datetime(UPDATED_DATETIME);
        InventoryStockDTO inventoryStockDTO = inventoryStockMapper.toDto(updatedInventoryStock);

        restInventoryStockMockMvc.perform(put("/api/inventory-stocks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryStockDTO)))
            .andExpect(status().isOk());

        // Validate the InventoryStock in the database
        List<InventoryStock> inventoryStockList = inventoryStockRepository.findAll();
        assertThat(inventoryStockList).hasSize(databaseSizeBeforeUpdate);
        InventoryStock testInventoryStock = inventoryStockList.get(inventoryStockList.size() - 1);
        assertThat(testInventoryStock.getStockCount()).isEqualTo(UPDATED_STOCK_COUNT);
        assertThat(testInventoryStock.getDatetime()).isEqualTo(UPDATED_DATETIME);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryStock() throws Exception {
        int databaseSizeBeforeUpdate = inventoryStockRepository.findAll().size();

        // Create the InventoryStock
        InventoryStockDTO inventoryStockDTO = inventoryStockMapper.toDto(inventoryStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryStockMockMvc.perform(put("/api/inventory-stocks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryStock in the database
        List<InventoryStock> inventoryStockList = inventoryStockRepository.findAll();
        assertThat(inventoryStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryStock() throws Exception {
        // Initialize the database
        inventoryStockRepository.saveAndFlush(inventoryStock);

        int databaseSizeBeforeDelete = inventoryStockRepository.findAll().size();

        // Delete the inventoryStock
        restInventoryStockMockMvc.perform(delete("/api/inventory-stocks/{id}", inventoryStock.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryStock> inventoryStockList = inventoryStockRepository.findAll();
        assertThat(inventoryStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
