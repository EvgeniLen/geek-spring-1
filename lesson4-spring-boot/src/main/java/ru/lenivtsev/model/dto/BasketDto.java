package ru.lenivtsev.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto {

    private Long id;

    private UserDto owner;

    //private List<ProductDto> productList = new ArrayList<>();

}
