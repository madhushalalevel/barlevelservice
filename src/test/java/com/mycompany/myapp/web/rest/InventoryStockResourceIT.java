package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BarLevelServiceApp;
import com.mycompany.myapp.domain.InventoryStock;
import com.mycompany.myapp.repository.InventoryStockRepository;
import com.mycompany.myapp.service.InventoryStockService;
import com.mycompany.myapp.service.dto.InventoryStockDTO;
import com.mycompany.myapp.service.mapper.InventoryStockMapper;
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
 * Integration tests for the {@Link InventoryStockResource} REST controller.
 */
@SpringBootTest(classes = BarLevelServiceApp.class)
public class InventoryStockResourceIT {

    private static final Integer DEFAULT_INVENTORY_ID = 1;
    private static final Integer UPDATED_INVENTORY_ID = 2;

    private static final Integer DEFAULT_PRODUCT_ID = 1;
    private static final Integer UPDATED_PRODUCT_ID = 2;

    private static final Integer DEFAULT_STOCK_COUNT = 1;
    private static final Integer UPDATED_STOCK_COUNT = 2;

    private static final Long DEFAULT_DATETIME = 1L;
    private static final Long UPDATED_DATETIME = 2L;

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
            .inventoryId(DEFAULT_INVENTORY_ID)
            .productID(DEFAULT_PRODUCT_ID)
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
            .inventoryId(UPDATED_INVENTORY_ID)
            .productID(UPDATED_PRODUCT_ID)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryStockDTO)))
            .andExpect(status().isCreated());

        // Validate the InventoryStock in the database
        List<InventoryStock> inventoryStockList = inventoryStockRepository.findAll();
        assertThat(inventoryStockList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryStock testInventoryStock = inventoryStockList.get(inventoryStockList.size() - 1);
        assertThat(testInventoryStock.getInventoryId()).isEqualTo(DEFAULT_INVENTORY_ID);
        assertThat(testInventoryStock.getProductID()).isEqualTo(DEFAULT_PRODUCT_ID);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoryId").value(hasItem(DEFAULT_INVENTORY_ID)))
            .andExpect(jsonPath("$.[*].productID").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].stockCount").value(hasItem(DEFAULT_STOCK_COUNT)))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(DEFAULT_DATETIME.intValue())));
    }
    
    @Test
    @Transactional
    public void getInventoryStock() throws Exception {
        // Initialize the database
        inventoryStockRepository.saveAndFlush(inventoryStock);

        // Get the inventoryStock
        restInventoryStockMockMvc.perform(get("/api/inventory-stocks/{id}", inventoryStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryStock.getId().intValue()))
            .andExpect(jsonPath("$.inventoryId").value(DEFAULT_INVENTORY_ID))
            .andExpect(jsonPath("$.productID").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.stockCount").value(DEFAULT_STOCK_COUNT))
            .andExpect(jsonPath("$.datetime").value(DEFAULT_DATETIME.intValue()));
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
            .inventoryId(UPDATED_INVENTORY_ID)
            .productID(UPDATED_PRODUCT_ID)
            .stockCount(UPDATED_STOCK_COUNT)
            .datetime(UPDATED_DATETIME);
        InventoryStockDTO inventoryStockDTO = inventoryStockMapper.toDto(updatedInventoryStock);

        restInventoryStockMockMvc.perform(put("/api/inventory-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryStockDTO)))
            .andExpect(status().isOk());

        // Validate the InventoryStock in the database
        List<InventoryStock> inventoryStockList = inventoryStockRepository.findAll();
        assertThat(inventoryStockList).hasSize(databaseSizeBeforeUpdate);
        InventoryStock testInventoryStock = inventoryStockList.get(inventoryStockList.size() - 1);
        assertThat(testInventoryStock.getInventoryId()).isEqualTo(UPDATED_INVENTORY_ID);
        assertThat(testInventoryStock.getProductID()).isEqualTo(UPDATED_PRODUCT_ID);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<InventoryStock> inventoryStockList = inventoryStockRepository.findAll();
        assertThat(inventoryStockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryStock.class);
        InventoryStock inventoryStock1 = new InventoryStock();
        inventoryStock1.setId(1L);
        InventoryStock inventoryStock2 = new InventoryStock();
        inventoryStock2.setId(inventoryStock1.getId());
        assertThat(inventoryStock1).isEqualTo(inventoryStock2);
        inventoryStock2.setId(2L);
        assertThat(inventoryStock1).isNotEqualTo(inventoryStock2);
        inventoryStock1.setId(null);
        assertThat(inventoryStock1).isNotEqualTo(inventoryStock2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryStockDTO.class);
        InventoryStockDTO inventoryStockDTO1 = new InventoryStockDTO();
        inventoryStockDTO1.setId(1L);
        InventoryStockDTO inventoryStockDTO2 = new InventoryStockDTO();
        assertThat(inventoryStockDTO1).isNotEqualTo(inventoryStockDTO2);
        inventoryStockDTO2.setId(inventoryStockDTO1.getId());
        assertThat(inventoryStockDTO1).isEqualTo(inventoryStockDTO2);
        inventoryStockDTO2.setId(2L);
        assertThat(inventoryStockDTO1).isNotEqualTo(inventoryStockDTO2);
        inventoryStockDTO1.setId(null);
        assertThat(inventoryStockDTO1).isNotEqualTo(inventoryStockDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inventoryStockMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inventoryStockMapper.fromId(null)).isNull();
    }
}
