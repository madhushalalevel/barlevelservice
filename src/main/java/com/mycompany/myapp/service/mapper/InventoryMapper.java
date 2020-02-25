package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.InventoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventory} and its DTO {@link InventoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {


    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Inventory toEntity(InventoryDTO inventoryDTO);

    default Inventory fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setId(id);
        return inventory;
    }
}
