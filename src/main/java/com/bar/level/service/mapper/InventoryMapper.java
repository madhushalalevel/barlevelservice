package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.InventoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventory} and its DTO {@link InventoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, BranchMapper.class, ZoneMapper.class, ShelfMapper.class})
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "shelf.id", target = "shelfId")
    InventoryDTO toDto(Inventory inventory);

    @Mapping(source = "productId", target = "product")
    @Mapping(target = "inventoryStock", ignore = true)
    @Mapping(source = "branchId", target = "branch")
    @Mapping(source = "zoneId", target = "zone")
    @Mapping(source = "shelfId", target = "shelf")
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
