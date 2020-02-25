package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProductPositionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPositions.class);
        ProductPositions productPositions1 = new ProductPositions();
        productPositions1.setId(1L);
        ProductPositions productPositions2 = new ProductPositions();
        productPositions2.setId(productPositions1.getId());
        assertThat(productPositions1).isEqualTo(productPositions2);
        productPositions2.setId(2L);
        assertThat(productPositions1).isNotEqualTo(productPositions2);
        productPositions1.setId(null);
        assertThat(productPositions1).isNotEqualTo(productPositions2);
    }
}
