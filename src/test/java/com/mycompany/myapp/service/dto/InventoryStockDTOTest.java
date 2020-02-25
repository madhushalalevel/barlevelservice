package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class InventoryStockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryStockDTO.class);
        InventoryStockDTO inventoryStockDTO1 = new InventoryStockDTO();
        inventoryStockDTO1.setId(1L);
        InventoryStockDTO inventoryStockDTO2 = new InventoryStockDTO();
        assertThat(inventoryStockDTO1).isNotEqualTo(inventoryStockDTO2);
        inventoryStockDTO2.setId(inventoryStockDTO1.getId());
        assertThat(inventoryStockDTO1).isEqualTo(inventoryStockDTO2);
        inventoryStockDTO2.setId(2L);
        assertThat(inventoryStockDTO1).isNotEqualTo(inventoryStockDTO2);
        inventoryStockDTO1.setId(null);
        assertThat(inventoryStockDTO1).isNotEqualTo(inventoryStockDTO2);
    }
}
