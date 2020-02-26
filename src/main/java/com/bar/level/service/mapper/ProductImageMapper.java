package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.ProductImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductImage} and its DTO {@link ProductImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductImageMapper extends EntityMapper<ProductImageDTO, ProductImage> {

    @Mapping(source = "product.id", target = "productId")
    ProductImageDTO toDto(ProductImage productImage);

    @Mapping(source = "productId", target = "product")
    ProductImage toEntity(ProductImageDTO productImageDTO);

    default ProductImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductImage productImage = new ProductImage();
        productImage.setId(id);
        return productImage;
    }
}
