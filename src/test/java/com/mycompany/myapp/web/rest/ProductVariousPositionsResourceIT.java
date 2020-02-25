package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BarLevelServiceApp;
import com.mycompany.myapp.domain.ProductVariousPositions;
import com.mycompany.myapp.repository.ProductVariousPositionsRepository;
import com.mycompany.myapp.service.ProductVariousPositionsService;
import com.mycompany.myapp.service.dto.ProductVariousPositionsDTO;
import com.mycompany.myapp.service.mapper.ProductVariousPositionsMapper;
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
 * Integration tests for the {@link ProductVariousPositionsResource} REST controller.
 */
@SpringBootTest(classes = BarLevelServiceApp.class)
public class ProductVariousPositionsResourceIT {

    private static final Integer DEFAULT_X_AXIS = 1;
    private static final Integer UPDATED_X_AXIS = 2;

    private static final Integer DEFAULT_Y_AXIS = 1;
    private static final Integer UPDATED_Y_AXIS = 2;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private ProductVariousPositionsRepository productVariousPositionsRepository;

    @Autowired
    private ProductVariousPositionsMapper productVariousPositionsMapper;

    @Autowired
    private ProductVariousPositionsService productVariousPositionsService;

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

    private MockMvc restProductVariousPositionsMockMvc;

    private ProductVariousPositions productVariousPositions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductVariousPositionsResource productVariousPositionsResource = new ProductVariousPositionsResource(productVariousPositionsService);
        this.restProductVariousPositionsMockMvc = MockMvcBuilders.standaloneSetup(productVariousPositionsResource)
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
    public static ProductVariousPositions createEntity(EntityManager em) {
        ProductVariousPositions productVariousPositions = new ProductVariousPositions()
            .xAxis(DEFAULT_X_AXIS)
            .yAxis(DEFAULT_Y_AXIS)
            .order(DEFAULT_ORDER);
        return productVariousPositions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductVariousPositions createUpdatedEntity(EntityManager em) {
        ProductVariousPositions productVariousPositions = new ProductVariousPositions()
            .xAxis(UPDATED_X_AXIS)
            .yAxis(UPDATED_Y_AXIS)
            .order(UPDATED_ORDER);
        return productVariousPositions;
    }

    @BeforeEach
    public void initTest() {
        productVariousPositions = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductVariousPositions() throws Exception {
        int databaseSizeBeforeCreate = productVariousPositionsRepository.findAll().size();

        // Create the ProductVariousPositions
        ProductVariousPositionsDTO productVariousPositionsDTO = productVariousPositionsMapper.toDto(productVariousPositions);
        restProductVariousPositionsMockMvc.perform(post("/api/product-various-positions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productVariousPositionsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductVariousPositions in the database
        List<ProductVariousPositions> productVariousPositionsList = productVariousPositionsRepository.findAll();
        assertThat(productVariousPositionsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductVariousPositions testProductVariousPositions = productVariousPositionsList.get(productVariousPositionsList.size() - 1);
        assertThat(testProductVariousPositions.getxAxis()).isEqualTo(DEFAULT_X_AXIS);
        assertThat(testProductVariousPositions.getyAxis()).isEqualTo(DEFAULT_Y_AXIS);
        assertThat(testProductVariousPositions.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createProductVariousPositionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productVariousPositionsRepository.findAll().size();

        // Create the ProductVariousPositions with an existing ID
        productVariousPositions.setId(1L);
        ProductVariousPositionsDTO productVariousPositionsDTO = productVariousPositionsMapper.toDto(productVariousPositions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductVariousPositionsMockMvc.perform(post("/api/product-various-positions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productVariousPositionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductVariousPositions in the database
        List<ProductVariousPositions> productVariousPositionsList = productVariousPositionsRepository.findAll();
        assertThat(productVariousPositionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductVariousPositions() throws Exception {
        // Initialize the database
        productVariousPositionsRepository.saveAndFlush(productVariousPositions);

        // Get all the productVariousPositionsList
        restProductVariousPositionsMockMvc.perform(get("/api/product-various-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productVariousPositions.getId().intValue())))
            .andExpect(jsonPath("$.[*].xAxis").value(hasItem(DEFAULT_X_AXIS)))
            .andExpect(jsonPath("$.[*].yAxis").value(hasItem(DEFAULT_Y_AXIS)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getProductVariousPositions() throws Exception {
        // Initialize the database
        productVariousPositionsRepository.saveAndFlush(productVariousPositions);

        // Get the productVariousPositions
        restProductVariousPositionsMockMvc.perform(get("/api/product-various-positions/{id}", productVariousPositions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productVariousPositions.getId().intValue()))
            .andExpect(jsonPath("$.xAxis").value(DEFAULT_X_AXIS))
            .andExpect(jsonPath("$.yAxis").value(DEFAULT_Y_AXIS))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingProductVariousPositions() throws Exception {
        // Get the productVariousPositions
        restProductVariousPositionsMockMvc.perform(get("/api/product-various-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductVariousPositions() throws Exception {
        // Initialize the database
        productVariousPositionsRepository.saveAndFlush(productVariousPositions);

        int databaseSizeBeforeUpdate = productVariousPositionsRepository.findAll().size();

        // Update the productVariousPositions
        ProductVariousPositions updatedProductVariousPositions = productVariousPositionsRepository.findById(productVariousPositions.getId()).get();
        // Disconnect from session so that the updates on updatedProductVariousPositions are not directly saved in db
        em.detach(updatedProductVariousPositions);
        updatedProductVariousPositions
            .xAxis(UPDATED_X_AXIS)
            .yAxis(UPDATED_Y_AXIS)
            .order(UPDATED_ORDER);
        ProductVariousPositionsDTO productVariousPositionsDTO = productVariousPositionsMapper.toDto(updatedProductVariousPositions);

        restProductVariousPositionsMockMvc.perform(put("/api/product-various-positions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productVariousPositionsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductVariousPositions in the database
        List<ProductVariousPositions> productVariousPositionsList = productVariousPositionsRepository.findAll();
        assertThat(productVariousPositionsList).hasSize(databaseSizeBeforeUpdate);
        ProductVariousPositions testProductVariousPositions = productVariousPositionsList.get(productVariousPositionsList.size() - 1);
        assertThat(testProductVariousPositions.getxAxis()).isEqualTo(UPDATED_X_AXIS);
        assertThat(testProductVariousPositions.getyAxis()).isEqualTo(UPDATED_Y_AXIS);
        assertThat(testProductVariousPositions.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingProductVariousPositions() throws Exception {
        int databaseSizeBeforeUpdate = productVariousPositionsRepository.findAll().size();

        // Create the ProductVariousPositions
        ProductVariousPositionsDTO productVariousPositionsDTO = productVariousPositionsMapper.toDto(productVariousPositions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductVariousPositionsMockMvc.perform(put("/api/product-various-positions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productVariousPositionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductVariousPositions in the database
        List<ProductVariousPositions> productVariousPositionsList = productVariousPositionsRepository.findAll();
        assertThat(productVariousPositionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductVariousPositions() throws Exception {
        // Initialize the database
        productVariousPositionsRepository.saveAndFlush(productVariousPositions);

        int databaseSizeBeforeDelete = productVariousPositionsRepository.findAll().size();

        // Delete the productVariousPositions
        restProductVariousPositionsMockMvc.perform(delete("/api/product-various-positions/{id}", productVariousPositions.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductVariousPositions> productVariousPositionsList = productVariousPositionsRepository.findAll();
        assertThat(productVariousPositionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
