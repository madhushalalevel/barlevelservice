package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ZoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Zone} and its DTO {@link ZoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {BranchMapper.class, InventoryMapper.class})
public interface ZoneMapper extends EntityMapper<ZoneDTO, Zone> {

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    @Mapping(source = "inventory.id", target = "inventoryId")
    ZoneDTO toDto(Zone zone);

    @Mapping(source = "branchId", target = "branch")
    @Mapping(source = "inventoryId", target = "inventory")
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
