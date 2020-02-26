package com.bar.level.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryStockMapperTest {

    private InventoryStockMapper inventoryStockMapper;

    @BeforeEach
    public void setUp() {
        inventoryStockMapper = new InventoryStockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inventoryStockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inventoryStockMapper.fromId(null)).isNull();
    }
}
