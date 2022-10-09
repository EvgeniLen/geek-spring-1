package ru.lenivtsev.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lenivtsev.model.dto.BasketDto;
import ru.lenivtsev.model.dto.UserDto;
import ru.lenivtsev.model.mapper.BasketDtoMapper;
import ru.lenivtsev.model.mapper.UserDtoMapper;
import ru.lenivtsev.repository.BasketRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketDtoMapper basketDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public void save(BasketDto basket) {
        basketRepository.save(basketDtoMapper.map(basket));
    };

    public Optional<BasketDto> findBasketById(long id) {
        return basketRepository.findById(id).map(basketDtoMapper::map);
    }

    public BasketDto findBasketByOwner(UserDto user) {
        long id = userDtoMapper.map(user).getId();
        return basketDtoMapper.map(basketRepository.findBasketByOwnerId(id));
    }


}
