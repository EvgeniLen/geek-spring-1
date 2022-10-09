package ru.lenivtsev.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.lenivtsev.model.Basket;
import ru.lenivtsev.model.dto.BasketDto;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BasketDtoMapper {

    BasketDto map(Basket basket);

    Basket map(BasketDto basketDto);
}
