package com.bar.level.web.rest;

import com.bar.level.BarlevelserviceApp;
import com.bar.level.domain.ProductPositions;
import com.bar.level.repository.ProductPositionsRepository;
import com.bar.level.service.ProductPositionsService;
import com.bar.level.service.dto.ProductPositionsDTO;
import com.bar.level.service.mapper.ProductPositionsMapper;
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
 * Integration tests for the {@link ProductPositionsResource} REST controller.
 */
@SpringBootTest(classes = BarlevelserviceApp.class)
public class ProductPositionsResourceIT {

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final ZonedDateTime DEFAULT_UPDATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ProductPositionsRepository productPositionsRepository;

    @Autowired
    private ProductPositionsMapper productPositionsMapper;

    @Autowired
    private ProductPositionsService productPositionsService;

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

    private MockMvc restProductPositionsMockMvc;

    private ProductPositions productPositions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductPositionsResource productPositionsResource = new ProductPositionsResource(productPositionsService);
        this.restProductPositionsMockMvc = MockMvcBuilders.standaloneSetup(productPositionsResource)
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
    public static ProductPositions createEntity(EntityManager em) {
        ProductPositions productPositions = new ProductPositions()
            .position(DEFAULT_POSITION)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return productPositions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPositions createUpdatedEntity(EntityManager em) {
        ProductPositions productPositions = new ProductPositions()
            .position(UPDATED_POSITION)
            .updatedTime(UPDATED_UPDATED_TIME);
        return productPositions;
    }

    @BeforeEach
    public void initTest() {
        productPositions = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductPositions() throws Exception {
        int databaseSizeBeforeCreate = productPositionsRepository.findAll().size();

        // Create the ProductPositions
        ProductPositionsDTO productPositionsDTO = productPositionsMapper.toDto(productPositions);
        restProductPositionsMockMvc.perform(post("/api/product-positions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPositionsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductPositions in the database
        List<ProductPositions> productPositionsList = productPositionsRepository.findAll();
        assertThat(productPositionsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPositions testProductPositions = productPositionsList.get(productPositionsList.size() - 1);
        assertThat(testProductPositions.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testProductPositions.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createProductPositionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productPositionsRepository.findAll().size();

        // Create the ProductPositions with an existing ID
        productPositions.setId(1L);
        ProductPositionsDTO productPositionsDTO = productPositionsMapper.toDto(productPositions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPositionsMockMvc.perform(post("/api/product-positions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPositionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPositions in the database
        List<ProductPositions> productPositionsList = productPositionsRepository.findAll();
        assertThat(productPositionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductPositions() throws Exception {
        // Initialize the database
        productPositionsRepository.saveAndFlush(productPositions);

        // Get all the productPositionsList
        restProductPositionsMockMvc.perform(get("/api/product-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPositions.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }
    
    @Test
    @Transactional
    public void getProductPositions() throws Exception {
        // Initialize the database
        productPositionsRepository.saveAndFlush(productPositions);

        // Get the productPositions
        restProductPositionsMockMvc.perform(get("/api/product-positions/{id}", productPositions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productPositions.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.updatedTime").value(sameInstant(DEFAULT_UPDATED_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingProductPositions() throws Exception {
        // Get the productPositions
        restProductPositionsMockMvc.perform(get("/api/product-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPositions() throws Exception {
        // Initialize the database
        productPositionsRepository.saveAndFlush(productPositions);

        int databaseSizeBeforeUpdate = productPositionsRepository.findAll().size();

        // Update the productPositions
        ProductPositions updatedProductPositions = productPositionsRepository.findById(productPositions.getId()).get();
        // Disconnect from session so that the updates on updatedProductPositions are not directly saved in db
        em.detach(updatedProductPositions);
        updatedProductPositions
            .position(UPDATED_POSITION)
            .updatedTime(UPDATED_UPDATED_TIME);
        ProductPositionsDTO productPositionsDTO = productPositionsMapper.toDto(updatedProductPositions);

        restProductPositionsMockMvc.perform(put("/api/product-positions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPositionsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductPositions in the database
        List<ProductPositions> productPositionsList = productPositionsRepository.findAll();
        assertThat(productPositionsList).hasSize(databaseSizeBeforeUpdate);
        ProductPositions testProductPositions = productPositionsList.get(productPositionsList.size() - 1);
        assertThat(testProductPositions.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testProductPositions.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductPositions() throws Exception {
        int databaseSizeBeforeUpdate = productPositionsRepository.findAll().size();

        // Create the ProductPositions
        ProductPositionsDTO productPositionsDTO = productPositionsMapper.toDto(productPositions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPositionsMockMvc.perform(put("/api/product-positions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPositionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPositions in the database
        List<ProductPositions> productPositionsList = productPositionsRepository.findAll();
        assertThat(productPositionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductPositions() throws Exception {
        // Initialize the database
        productPositionsRepository.saveAndFlush(productPositions);

        int databaseSizeBeforeDelete = productPositionsRepository.findAll().size();

        // Delete the productPositions
        restProductPositionsMockMvc.perform(delete("/api/product-positions/{id}", productPositions.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductPositions> productPositionsList = productPositionsRepository.findAll();
        assertThat(productPositionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
