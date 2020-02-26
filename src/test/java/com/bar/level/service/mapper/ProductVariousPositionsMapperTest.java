package com.bar.level.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductVariousPositionsMapperTest {

    private ProductVariousPositionsMapper productVariousPositionsMapper;

    @BeforeEach
    public void setUp() {
        productVariousPositionsMapper = new ProductVariousPositionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productVariousPositionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productVariousPositionsMapper.fromId(null)).isNull();
    }
}
