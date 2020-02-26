package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {


    @Mapping(target = "productPositions", ignore = true)
    @Mapping(target = "removeProductPositions", ignore = true)
    @Mapping(target = "productImage", ignore = true)
    @Mapping(target = "inventory", ignore = true)
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
