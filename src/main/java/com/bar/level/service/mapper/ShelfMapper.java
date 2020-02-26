package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.ShelfDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shelf} and its DTO {@link ShelfDTO}.
 */
@Mapper(componentModel = "spring", uses = {ZoneMapper.class})
public interface ShelfMapper extends EntityMapper<ShelfDTO, Shelf> {

    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.name", target = "zoneName")
    ShelfDTO toDto(Shelf shelf);

    @Mapping(target = "inventories", ignore = true)
    @Mapping(target = "removeInventory", ignore = true)
    @Mapping(source = "zoneId", target = "zone")
    Shelf toEntity(ShelfDTO shelfDTO);

    default Shelf fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shelf shelf = new Shelf();
        shelf.setId(id);
        return shelf;
    }
}
