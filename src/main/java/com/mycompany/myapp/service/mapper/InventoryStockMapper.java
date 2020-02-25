package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.InventoryStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryStock} and its DTO {@link InventoryStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventoryStockMapper extends EntityMapper<InventoryStockDTO, InventoryStock> {



    default InventoryStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        InventoryStock inventoryStock = new InventoryStock();
        inventoryStock.setId(id);
        return inventoryStock;
    }
}
