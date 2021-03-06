package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.ProductVariousPositionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductVariousPositions} and its DTO {@link ProductVariousPositionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductVariousPositionsMapper extends EntityMapper<ProductVariousPositionsDTO, ProductVariousPositions> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductVariousPositionsDTO toDto(ProductVariousPositions productVariousPositions);

    @Mapping(source = "productId", target = "product")
    ProductVariousPositions toEntity(ProductVariousPositionsDTO productVariousPositionsDTO);

    default ProductVariousPositions fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductVariousPositions productVariousPositions = new ProductVariousPositions();
        productVariousPositions.setId(id);
        return productVariousPositions;
    }
}
