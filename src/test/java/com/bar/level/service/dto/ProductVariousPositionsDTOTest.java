package com.bar.level.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bar.level.web.rest.TestUtil;

public class ProductVariousPositionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductVariousPositionsDTO.class);
        ProductVariousPositionsDTO productVariousPositionsDTO1 = new ProductVariousPositionsDTO();
        productVariousPositionsDTO1.setId(1L);
        ProductVariousPositionsDTO productVariousPositionsDTO2 = new ProductVariousPositionsDTO();
        assertThat(productVariousPositionsDTO1).isNotEqualTo(productVariousPositionsDTO2);
        productVariousPositionsDTO2.setId(productVariousPositionsDTO1.getId());
        assertThat(productVariousPositionsDTO1).isEqualTo(productVariousPositionsDTO2);
        productVariousPositionsDTO2.setId(2L);
        assertThat(productVariousPositionsDTO1).isNotEqualTo(productVariousPositionsDTO2);
        productVariousPositionsDTO1.setId(null);
        assertThat(productVariousPositionsDTO1).isNotEqualTo(productVariousPositionsDTO2);
    }
}
