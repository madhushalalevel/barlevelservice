package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProductPositionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductPositions} and its DTO {@link ProductPositionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductPositionsMapper extends EntityMapper<ProductPositionsDTO, ProductPositions> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductPositionsDTO toDto(ProductPositions productPositions);

    @Mapping(source = "productId", target = "product")
    ProductPositions toEntity(ProductPositionsDTO productPositionsDTO);

    default ProductPositions fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductPositions productPositions = new ProductPositions();
        productPositions.setId(id);
        return productPositions;
    }
}
