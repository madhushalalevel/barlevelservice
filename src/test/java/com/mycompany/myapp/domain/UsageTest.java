package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class UsageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usage.class);
        Usage usage1 = new Usage();
        usage1.setId(1L);
        Usage usage2 = new Usage();
        usage2.setId(usage1.getId());
        assertThat(usage1).isEqualTo(usage2);
        usage2.setId(2L);
        assertThat(usage1).isNotEqualTo(usage2);
        usage1.setId(null);
        assertThat(usage1).isNotEqualTo(usage2);
    }
}
