package com.bar.level.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductImageMapperTest {

    private ProductImageMapper productImageMapper;

    @BeforeEach
    public void setUp() {
        productImageMapper = new ProductImageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productImageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productImageMapper.fromId(null)).isNull();
    }
}
