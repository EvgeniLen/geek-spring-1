package ru.lenivtsev.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import ru.lenivtsev.model.Product;
import ru.lenivtsev.model.dto.ProductDto;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductDtoMapper {

    ProductDto map(Product product);

    //@Mapping(target = "id", ignore = true)
    Product map(ProductDto productDto);

}
