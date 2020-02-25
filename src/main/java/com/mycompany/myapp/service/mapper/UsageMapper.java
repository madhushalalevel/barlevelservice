package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.UsageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Usage} and its DTO {@link UsageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsageMapper extends EntityMapper<UsageDTO, Usage> {



    default Usage fromId(Long id) {
        if (id == null) {
            return null;
        }
        Usage usage = new Usage();
        usage.setId(id);
        return usage;
    }
}
