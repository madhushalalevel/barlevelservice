package com.bar.level.web.rest;

import com.bar.level.BarlevelserviceApp;
import com.bar.level.domain.Usage;
import com.bar.level.repository.UsageRepository;
import com.bar.level.service.UsageService;
import com.bar.level.service.dto.UsageDTO;
import com.bar.level.service.mapper.UsageMapper;
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
 * Integration tests for the {@link UsageResource} REST controller.
 */
@SpringBootTest(classes = BarlevelserviceApp.class)
public class UsageResourceIT {

    private static final Integer DEFAULT_USAGE_ID = 1;
    private static final Integer UPDATED_USAGE_ID = 2;

    private static final Integer DEFAULT_PRODUCT_ID = 1;
    private static final Integer UPDATED_PRODUCT_ID = 2;

    private static final Integer DEFAULT_BRANCH_ID = 1;
    private static final Integer UPDATED_BRANCH_ID = 2;

    private static final Integer DEFAULT_ZONE_ID = 1;
    private static final Integer UPDATED_ZONE_ID = 2;

    private static final Integer DEFAULT_SHELF_ID = 1;
    private static final Integer UPDATED_SHELF_ID = 2;

    private static final Integer DEFAULT_USAGE = 1;
    private static final Integer UPDATED_USAGE = 2;

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private UsageMapper usageMapper;

    @Autowired
    private UsageService usageService;

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

    private MockMvc restUsageMockMvc;

    private Usage usage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsageResource usageResource = new UsageResource(usageService);
        this.restUsageMockMvc = MockMvcBuilders.standaloneSetup(usageResource)
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
    public static Usage createEntity(EntityManager em) {
        Usage usage = new Usage()
            .usageId(DEFAULT_USAGE_ID)
            .productID(DEFAULT_PRODUCT_ID)
            .branchID(DEFAULT_BRANCH_ID)
            .zoneID(DEFAULT_ZONE_ID)
            .shelfID(DEFAULT_SHELF_ID)
            .usage(DEFAULT_USAGE)
            .datetime(DEFAULT_DATETIME);
        return usage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usage createUpdatedEntity(EntityManager em) {
        Usage usage = new Usage()
            .usageId(UPDATED_USAGE_ID)
            .productID(UPDATED_PRODUCT_ID)
            .branchID(UPDATED_BRANCH_ID)
            .zoneID(UPDATED_ZONE_ID)
            .shelfID(UPDATED_SHELF_ID)
            .usage(UPDATED_USAGE)
            .datetime(UPDATED_DATETIME);
        return usage;
    }

    @BeforeEach
    public void initTest() {
        usage = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsage() throws Exception {
        int databaseSizeBeforeCreate = usageRepository.findAll().size();

        // Create the Usage
        UsageDTO usageDTO = usageMapper.toDto(usage);
        restUsageMockMvc.perform(post("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usageDTO)))
            .andExpect(status().isCreated());

        // Validate the Usage in the database
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeCreate + 1);
        Usage testUsage = usageList.get(usageList.size() - 1);
        assertThat(testUsage.getUsageId()).isEqualTo(DEFAULT_USAGE_ID);
        assertThat(testUsage.getProductID()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testUsage.getBranchID()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testUsage.getZoneID()).isEqualTo(DEFAULT_ZONE_ID);
        assertThat(testUsage.getShelfID()).isEqualTo(DEFAULT_SHELF_ID);
        assertThat(testUsage.getUsage()).isEqualTo(DEFAULT_USAGE);
        assertThat(testUsage.getDatetime()).isEqualTo(DEFAULT_DATETIME);
    }

    @Test
    @Transactional
    public void createUsageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usageRepository.findAll().size();

        // Create the Usage with an existing ID
        usage.setId(1L);
        UsageDTO usageDTO = usageMapper.toDto(usage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsageMockMvc.perform(post("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usage in the database
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsages() throws Exception {
        // Initialize the database
        usageRepository.saveAndFlush(usage);

        // Get all the usageList
        restUsageMockMvc.perform(get("/api/usages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usage.getId().intValue())))
            .andExpect(jsonPath("$.[*].usageId").value(hasItem(DEFAULT_USAGE_ID)))
            .andExpect(jsonPath("$.[*].productID").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].branchID").value(hasItem(DEFAULT_BRANCH_ID)))
            .andExpect(jsonPath("$.[*].zoneID").value(hasItem(DEFAULT_ZONE_ID)))
            .andExpect(jsonPath("$.[*].shelfID").value(hasItem(DEFAULT_SHELF_ID)))
            .andExpect(jsonPath("$.[*].usage").value(hasItem(DEFAULT_USAGE)))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(sameInstant(DEFAULT_DATETIME))));
    }
    
    @Test
    @Transactional
    public void getUsage() throws Exception {
        // Initialize the database
        usageRepository.saveAndFlush(usage);

        // Get the usage
        restUsageMockMvc.perform(get("/api/usages/{id}", usage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usage.getId().intValue()))
            .andExpect(jsonPath("$.usageId").value(DEFAULT_USAGE_ID))
            .andExpect(jsonPath("$.productID").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.branchID").value(DEFAULT_BRANCH_ID))
            .andExpect(jsonPath("$.zoneID").value(DEFAULT_ZONE_ID))
            .andExpect(jsonPath("$.shelfID").value(DEFAULT_SHELF_ID))
            .andExpect(jsonPath("$.usage").value(DEFAULT_USAGE))
            .andExpect(jsonPath("$.datetime").value(sameInstant(DEFAULT_DATETIME)));
    }

    @Test
    @Transactional
    public void getNonExistingUsage() throws Exception {
        // Get the usage
        restUsageMockMvc.perform(get("/api/usages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsage() throws Exception {
        // Initialize the database
        usageRepository.saveAndFlush(usage);

        int databaseSizeBeforeUpdate = usageRepository.findAll().size();

        // Update the usage
        Usage updatedUsage = usageRepository.findById(usage.getId()).get();
        // Disconnect from session so that the updates on updatedUsage are not directly saved in db
        em.detach(updatedUsage);
        updatedUsage
            .usageId(UPDATED_USAGE_ID)
            .productID(UPDATED_PRODUCT_ID)
            .branchID(UPDATED_BRANCH_ID)
            .zoneID(UPDATED_ZONE_ID)
            .shelfID(UPDATED_SHELF_ID)
            .usage(UPDATED_USAGE)
            .datetime(UPDATED_DATETIME);
        UsageDTO usageDTO = usageMapper.toDto(updatedUsage);

        restUsageMockMvc.perform(put("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usageDTO)))
            .andExpect(status().isOk());

        // Validate the Usage in the database
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeUpdate);
        Usage testUsage = usageList.get(usageList.size() - 1);
        assertThat(testUsage.getUsageId()).isEqualTo(UPDATED_USAGE_ID);
        assertThat(testUsage.getProductID()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testUsage.getBranchID()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testUsage.getZoneID()).isEqualTo(UPDATED_ZONE_ID);
        assertThat(testUsage.getShelfID()).isEqualTo(UPDATED_SHELF_ID);
        assertThat(testUsage.getUsage()).isEqualTo(UPDATED_USAGE);
        assertThat(testUsage.getDatetime()).isEqualTo(UPDATED_DATETIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUsage() throws Exception {
        int databaseSizeBeforeUpdate = usageRepository.findAll().size();

        // Create the Usage
        UsageDTO usageDTO = usageMapper.toDto(usage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsageMockMvc.perform(put("/api/usages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usage in the database
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsage() throws Exception {
        // Initialize the database
        usageRepository.saveAndFlush(usage);

        int databaseSizeBeforeDelete = usageRepository.findAll().size();

        // Delete the usage
        restUsageMockMvc.perform(delete("/api/usages/{id}", usage.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usage> usageList = usageRepository.findAll();
        assertThat(usageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
