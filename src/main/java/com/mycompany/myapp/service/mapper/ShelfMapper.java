package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ShelfDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shelf} and its DTO {@link ShelfDTO}.
 */
@Mapper(componentModel = "spring", uses = {ZoneMapper.class, InventoryMapper.class})
public interface ShelfMapper extends EntityMapper<ShelfDTO, Shelf> {

    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.name", target = "zoneName")
    @Mapping(source = "inventory.id", target = "inventoryId")
    ShelfDTO toDto(Shelf shelf);

    @Mapping(source = "zoneId", target = "zone")
    @Mapping(source = "inventoryId", target = "inventory")
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
