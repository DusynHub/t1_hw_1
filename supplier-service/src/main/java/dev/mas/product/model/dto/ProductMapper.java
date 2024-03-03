package dev.mas.product.model.dto;


import dev.mas.product.model.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")

public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Product newProductDtoToProduct(NewProductDto newProductDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    void updateProductFromUpdateProductDto(UpdateProductDto updateProductDto, @MappingTarget Product productFromDb);
}
