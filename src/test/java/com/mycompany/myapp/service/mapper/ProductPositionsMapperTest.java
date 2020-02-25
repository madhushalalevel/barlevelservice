package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductPositionsMapperTest {

    private ProductPositionsMapper productPositionsMapper;

    @BeforeEach
    public void setUp() {
        productPositionsMapper = new ProductPositionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productPositionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productPositionsMapper.fromId(null)).isNull();
    }
}
