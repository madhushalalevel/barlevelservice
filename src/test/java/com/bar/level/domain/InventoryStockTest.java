package com.bar.level.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bar.level.web.rest.TestUtil;

public class InventoryStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryStock.class);
        InventoryStock inventoryStock1 = new InventoryStock();
        inventoryStock1.setId(1L);
        InventoryStock inventoryStock2 = new InventoryStock();
        inventoryStock2.setId(inventoryStock1.getId());
        assertThat(inventoryStock1).isEqualTo(inventoryStock2);
        inventoryStock2.setId(2L);
        assertThat(inventoryStock1).isNotEqualTo(inventoryStock2);
        inventoryStock1.setId(null);
        assertThat(inventoryStock1).isNotEqualTo(inventoryStock2);
    }
}
