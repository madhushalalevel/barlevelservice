package com.bar.level.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bar.level.web.rest.TestUtil;

public class ProductPositionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPositionsDTO.class);
        ProductPositionsDTO productPositionsDTO1 = new ProductPositionsDTO();
        productPositionsDTO1.setId(1L);
        ProductPositionsDTO productPositionsDTO2 = new ProductPositionsDTO();
        assertThat(productPositionsDTO1).isNotEqualTo(productPositionsDTO2);
        productPositionsDTO2.setId(productPositionsDTO1.getId());
        assertThat(productPositionsDTO1).isEqualTo(productPositionsDTO2);
        productPositionsDTO2.setId(2L);
        assertThat(productPositionsDTO1).isNotEqualTo(productPositionsDTO2);
        productPositionsDTO1.setId(null);
        assertThat(productPositionsDTO1).isNotEqualTo(productPositionsDTO2);
    }
}
