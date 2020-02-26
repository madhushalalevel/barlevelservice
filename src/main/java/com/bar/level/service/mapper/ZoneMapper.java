package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.ZoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Zone} and its DTO {@link ZoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {BranchMapper.class})
public interface ZoneMapper extends EntityMapper<ZoneDTO, Zone> {

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    ZoneDTO toDto(Zone zone);

    @Mapping(target = "inventories", ignore = true)
    @Mapping(target = "removeInventory", ignore = true)
    @Mapping(source = "branchId", target = "branch")
    Zone toEntity(ZoneDTO zoneDTO);

    default Zone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Zone zone = new Zone();
        zone.setId(id);
        return zone;
    }
}
