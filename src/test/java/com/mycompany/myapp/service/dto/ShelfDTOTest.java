package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ShelfDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelfDTO.class);
        ShelfDTO shelfDTO1 = new ShelfDTO();
        shelfDTO1.setId(1L);
        ShelfDTO shelfDTO2 = new ShelfDTO();
        assertThat(shelfDTO1).isNotEqualTo(shelfDTO2);
        shelfDTO2.setId(shelfDTO1.getId());
        assertThat(shelfDTO1).isEqualTo(shelfDTO2);
        shelfDTO2.setId(2L);
        assertThat(shelfDTO1).isNotEqualTo(shelfDTO2);
        shelfDTO1.setId(null);
        assertThat(shelfDTO1).isNotEqualTo(shelfDTO2);
    }
}
