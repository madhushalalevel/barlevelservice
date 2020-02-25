package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UsageMapperTest {

    private UsageMapper usageMapper;

    @BeforeEach
    public void setUp() {
        usageMapper = new UsageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(usageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(usageMapper.fromId(null)).isNull();
    }
}
