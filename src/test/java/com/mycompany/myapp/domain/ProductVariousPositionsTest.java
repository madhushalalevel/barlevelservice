package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProductVariousPositionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductVariousPositions.class);
        ProductVariousPositions productVariousPositions1 = new ProductVariousPositions();
        productVariousPositions1.setId(1L);
        ProductVariousPositions productVariousPositions2 = new ProductVariousPositions();
        productVariousPositions2.setId(productVariousPositions1.getId());
        assertThat(productVariousPositions1).isEqualTo(productVariousPositions2);
        productVariousPositions2.setId(2L);
        assertThat(productVariousPositions1).isNotEqualTo(productVariousPositions2);
        productVariousPositions1.setId(null);
        assertThat(productVariousPositions1).isNotEqualTo(productVariousPositions2);
    }
}
