package com.bar.level.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShelfMapperTest {

    private ShelfMapper shelfMapper;

    @BeforeEach
    public void setUp() {
        shelfMapper = new ShelfMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(shelfMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(shelfMapper.fromId(null)).isNull();
    }
}
