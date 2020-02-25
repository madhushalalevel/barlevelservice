package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BarLevelServiceApp;
import com.mycompany.myapp.domain.Inventory;
import com.mycompany.myapp.repository.InventoryRepository;
import com.mycompany.myapp.service.InventoryService;
import com.mycompany.myapp.service.dto.InventoryDTO;
import com.mycompany.myapp.service.mapper.InventoryMapper;
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
 * Integration tests for the {@link InventoryResource} REST controller.
 */
@SpringBootTest(classes = BarLevelServiceApp.class)
public class InventoryResourceIT {

    private static final Integer DEFAULT_PRODUCT_ID = 1;
    private static final Integer UPDATED_PRODUCT_ID = 2;

    private static final Integer DEFAULT_TENNENT_ID = 1;
    private static final Integer UPDATED_TENNENT_ID = 2;

    private static final Integer DEFAULT_BRANCH_ID = 1;
    private static final Integer UPDATED_BRANCH_ID = 2;

    private static final Integer DEFAULT_ZONE_ID = 1;
    private static final Integer UPDATED_ZONE_ID = 2;

    private static final Integer DEFAULT_SHELF_ID = 1;
    private static final Integer UPDATED_SHELF_ID = 2;

    private static final Integer DEFAULT_CURRENT_STOCK_COUNT = 1;
    private static final Integer UPDATED_CURRENT_STOCK_COUNT = 2;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private InventoryService inventoryService;

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

    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryResource inventoryResource = new InventoryResource(inventoryService);
        this.restInventoryMockMvc = MockMvcBuilders.standaloneSetup(inventoryResource)
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
    public static Inventory createEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .productID(DEFAULT_PRODUCT_ID)
            .tennentID(DEFAULT_TENNENT_ID)
            .branchID(DEFAULT_BRANCH_ID)
            .zoneID(DEFAULT_ZONE_ID)
            .shelfID(DEFAULT_SHELF_ID)
            .currentStockCount(DEFAULT_CURRENT_STOCK_COUNT);
        return inventory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createUpdatedEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .productID(UPDATED_PRODUCT_ID)
            .tennentID(UPDATED_TENNENT_ID)
            .branchID(UPDATED_BRANCH_ID)
            .zoneID(UPDATED_ZONE_ID)
            .shelfID(UPDATED_SHELF_ID)
            .currentStockCount(UPDATED_CURRENT_STOCK_COUNT);
        return inventory;
    }

    @BeforeEach
    public void initTest() {
        inventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventory() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getProductID()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testInventory.getTennentID()).isEqualTo(DEFAULT_TENNENT_ID);
        assertThat(testInventory.getBranchID()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testInventory.getZoneID()).isEqualTo(DEFAULT_ZONE_ID);
        assertThat(testInventory.getShelfID()).isEqualTo(DEFAULT_SHELF_ID);
        assertThat(testInventory.getCurrentStockCount()).isEqualTo(DEFAULT_CURRENT_STOCK_COUNT);
    }

    @Test
    @Transactional
    public void createInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory with an existing ID
        inventory.setId(1L);
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventories() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList
        restInventoryMockMvc.perform(get("/api/inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].productID").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].tennentID").value(hasItem(DEFAULT_TENNENT_ID)))
            .andExpect(jsonPath("$.[*].branchID").value(hasItem(DEFAULT_BRANCH_ID)))
            .andExpect(jsonPath("$.[*].zoneID").value(hasItem(DEFAULT_ZONE_ID)))
            .andExpect(jsonPath("$.[*].shelfID").value(hasItem(DEFAULT_SHELF_ID)))
            .andExpect(jsonPath("$.[*].currentStockCount").value(hasItem(DEFAULT_CURRENT_STOCK_COUNT)));
    }
    
    @Test
    @Transactional
    public void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
            .andExpect(jsonPath("$.productID").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.tennentID").value(DEFAULT_TENNENT_ID))
            .andExpect(jsonPath("$.branchID").value(DEFAULT_BRANCH_ID))
            .andExpect(jsonPath("$.zoneID").value(DEFAULT_ZONE_ID))
            .andExpect(jsonPath("$.shelfID").value(DEFAULT_SHELF_ID))
            .andExpect(jsonPath("$.currentStockCount").value(DEFAULT_CURRENT_STOCK_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findById(inventory.getId()).get();
        // Disconnect from session so that the updates on updatedInventory are not directly saved in db
        em.detach(updatedInventory);
        updatedInventory
            .productID(UPDATED_PRODUCT_ID)
            .tennentID(UPDATED_TENNENT_ID)
            .branchID(UPDATED_BRANCH_ID)
            .zoneID(UPDATED_ZONE_ID)
            .shelfID(UPDATED_SHELF_ID)
            .currentStockCount(UPDATED_CURRENT_STOCK_COUNT);
        InventoryDTO inventoryDTO = inventoryMapper.toDto(updatedInventory);

        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getProductID()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testInventory.getTennentID()).isEqualTo(UPDATED_TENNENT_ID);
        assertThat(testInventory.getBranchID()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testInventory.getZoneID()).isEqualTo(UPDATED_ZONE_ID);
        assertThat(testInventory.getShelfID()).isEqualTo(UPDATED_SHELF_ID);
        assertThat(testInventory.getCurrentStockCount()).isEqualTo(UPDATED_CURRENT_STOCK_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeDelete = inventoryRepository.findAll().size();

        // Delete the inventory
        restInventoryMockMvc.perform(delete("/api/inventories/{id}", inventory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
