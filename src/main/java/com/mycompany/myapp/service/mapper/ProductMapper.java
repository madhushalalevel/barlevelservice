package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {InventoryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "inventory.id", target = "inventoryId")
    ProductDTO toDto(Product product);

    @Mapping(target = "productPositions", ignore = true)
    @Mapping(target = "removeProductPositions", ignore = true)
    @Mapping(target = "productImage", ignore = true)
    @Mapping(source = "inventoryId", target = "inventory")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
