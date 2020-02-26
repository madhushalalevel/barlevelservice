package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.InventoryStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryStock} and its DTO {@link InventoryStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {InventoryMapper.class})
public interface InventoryStockMapper extends EntityMapper<InventoryStockDTO, InventoryStock> {

    @Mapping(source = "inventory.id", target = "inventoryId")
    InventoryStockDTO toDto(InventoryStock inventoryStock);

    @Mapping(source = "inventoryId", target = "inventory")
    InventoryStock toEntity(InventoryStockDTO inventoryStockDTO);

    default InventoryStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        InventoryStock inventoryStock = new InventoryStock();
        inventoryStock.setId(id);
        return inventoryStock;
    }
}
