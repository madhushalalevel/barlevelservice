package com.bar.level.web.rest;

import com.bar.level.BarlevelserviceApp;
import com.bar.level.domain.ProductImage;
import com.bar.level.repository.ProductImageRepository;
import com.bar.level.service.ProductImageService;
import com.bar.level.service.dto.ProductImageDTO;
import com.bar.level.service.mapper.ProductImageMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bar.level.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductImageResource} REST controller.
 */
@SpringBootTest(classes = BarlevelserviceApp.class)
public class ProductImageResourceIT {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ProductImageService productImageService;

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

    private MockMvc restProductImageMockMvc;

    private ProductImage productImage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductImageResource productImageResource = new ProductImageResource(productImageService);
        this.restProductImageMockMvc = MockMvcBuilders.standaloneSetup(productImageResource)
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
    public static ProductImage createEntity(EntityManager em) {
        ProductImage productImage = new ProductImage()
            .imageUrl(DEFAULT_IMAGE_URL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return productImage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductImage createUpdatedEntity(EntityManager em) {
        ProductImage productImage = new ProductImage()
            .imageUrl(UPDATED_IMAGE_URL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return productImage;
    }

    @BeforeEach
    public void initTest() {
        productImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductImage() throws Exception {
        int databaseSizeBeforeCreate = productImageRepository.findAll().size();

        // Create the ProductImage
        ProductImageDTO productImageDTO = productImageMapper.toDto(productImage);
        restProductImageMockMvc.perform(post("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImageDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductImage testProductImage = productImageList.get(productImageList.size() - 1);
        assertThat(testProductImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProductImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProductImage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProductImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productImageRepository.findAll().size();

        // Create the ProductImage with an existing ID
        productImage.setId(1L);
        ProductImageDTO productImageDTO = productImageMapper.toDto(productImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductImageMockMvc.perform(post("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductImages() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        // Get all the productImageList
        restProductImageMockMvc.perform(get("/api/product-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getProductImage() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        // Get the productImage
        restProductImageMockMvc.perform(get("/api/product-images/{id}", productImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingProductImage() throws Exception {
        // Get the productImage
        restProductImageMockMvc.perform(get("/api/product-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductImage() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        int databaseSizeBeforeUpdate = productImageRepository.findAll().size();

        // Update the productImage
        ProductImage updatedProductImage = productImageRepository.findById(productImage.getId()).get();
        // Disconnect from session so that the updates on updatedProductImage are not directly saved in db
        em.detach(updatedProductImage);
        updatedProductImage
            .imageUrl(UPDATED_IMAGE_URL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ProductImageDTO productImageDTO = productImageMapper.toDto(updatedProductImage);

        restProductImageMockMvc.perform(put("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImageDTO)))
            .andExpect(status().isOk());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeUpdate);
        ProductImage testProductImage = productImageList.get(productImageList.size() - 1);
        assertThat(testProductImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProductImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProductImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductImage() throws Exception {
        int databaseSizeBeforeUpdate = productImageRepository.findAll().size();

        // Create the ProductImage
        ProductImageDTO productImageDTO = productImageMapper.toDto(productImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductImageMockMvc.perform(put("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductImage() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        int databaseSizeBeforeDelete = productImageRepository.findAll().size();

        // Delete the productImage
        restProductImageMockMvc.perform(delete("/api/product-images/{id}", productImage.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
