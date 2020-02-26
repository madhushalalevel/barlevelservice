package com.bar.level.web.rest;

import com.bar.level.BarlevelserviceApp;
import com.bar.level.domain.Product;
import com.bar.level.repository.ProductRepository;
import com.bar.level.service.ProductService;
import com.bar.level.service.dto.ProductDTO;
import com.bar.level.service.mapper.ProductMapper;
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
import java.util.List;

import static com.bar.level.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bar.level.domain.enumeration.BarCodeType;
/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@SpringBootTest(classes = BarlevelserviceApp.class)
public class ProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BAR_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_CONTAINER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINER_TYPE = "BBBBBBBBBB";

    private static final BarCodeType DEFAULT_BAR_CODE_TYPE = BarCodeType.PUC;
    private static final BarCodeType UPDATED_BAR_CODE_TYPE = BarCodeType.EAN;

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

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

    private MockMvc restProductMockMvc;

    private Product product;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
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
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .barCode(DEFAULT_BAR_CODE)
            .quantity(DEFAULT_QUANTITY)
            .volume(DEFAULT_VOLUME)
            .type(DEFAULT_TYPE)
            .subType(DEFAULT_SUB_TYPE)
            .price(DEFAULT_PRICE)
            .containerType(DEFAULT_CONTAINER_TYPE)
            .barCodeType(DEFAULT_BAR_CODE_TYPE)
            .tenantId(DEFAULT_TENANT_ID);
        return product;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .name(UPDATED_NAME)
            .barCode(UPDATED_BAR_CODE)
            .quantity(UPDATED_QUANTITY)
            .volume(UPDATED_VOLUME)
            .type(UPDATED_TYPE)
            .subType(UPDATED_SUB_TYPE)
            .price(UPDATED_PRICE)
            .containerType(UPDATED_CONTAINER_TYPE)
            .barCodeType(UPDATED_BAR_CODE_TYPE)
            .tenantId(UPDATED_TENANT_ID);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getBarCode()).isEqualTo(DEFAULT_BAR_CODE);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testProduct.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProduct.getSubType()).isEqualTo(DEFAULT_SUB_TYPE);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getContainerType()).isEqualTo(DEFAULT_CONTAINER_TYPE);
        assertThat(testProduct.getBarCodeType()).isEqualTo(DEFAULT_BAR_CODE_TYPE);
        assertThat(testProduct.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].barCode").value(hasItem(DEFAULT_BAR_CODE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].subType").value(hasItem(DEFAULT_SUB_TYPE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].containerType").value(hasItem(DEFAULT_CONTAINER_TYPE)))
            .andExpect(jsonPath("$.[*].barCodeType").value(hasItem(DEFAULT_BAR_CODE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.barCode").value(DEFAULT_BAR_CODE))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.subType").value(DEFAULT_SUB_TYPE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.containerType").value(DEFAULT_CONTAINER_TYPE))
            .andExpect(jsonPath("$.barCodeType").value(DEFAULT_BAR_CODE_TYPE.toString()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .name(UPDATED_NAME)
            .barCode(UPDATED_BAR_CODE)
            .quantity(UPDATED_QUANTITY)
            .volume(UPDATED_VOLUME)
            .type(UPDATED_TYPE)
            .subType(UPDATED_SUB_TYPE)
            .price(UPDATED_PRICE)
            .containerType(UPDATED_CONTAINER_TYPE)
            .barCodeType(UPDATED_BAR_CODE_TYPE)
            .tenantId(UPDATED_TENANT_ID);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getBarCode()).isEqualTo(UPDATED_BAR_CODE);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testProduct.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProduct.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getContainerType()).isEqualTo(UPDATED_CONTAINER_TYPE);
        assertThat(testProduct.getBarCodeType()).isEqualTo(UPDATED_BAR_CODE_TYPE);
        assertThat(testProduct.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
