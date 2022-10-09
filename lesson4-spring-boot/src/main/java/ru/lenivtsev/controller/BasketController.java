package ru.lenivtsev.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lenivtsev.model.User;
import ru.lenivtsev.model.dto.BasketDto;
import ru.lenivtsev.model.dto.ProductDto;
import ru.lenivtsev.model.dto.UserDto;
import ru.lenivtsev.service.BasketService;
import ru.lenivtsev.service.ProductService;
import ru.lenivtsev.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PostUpdate;
import javax.validation.Valid;


@Slf4j
@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final ProductService productService;

    @PostMapping("/{basketId}/{productId}")
    public String addProductToBasket(@PathVariable long basketId,
                                   @PathVariable long productId){
        BasketDto basketDto = basketService.findBasketById(basketId).get();
        ProductDto productDto = productService.findByProductId(productId).get();
        //basketDto.getProductList().add(productDto);
        //basketService.save(basketDto);
        productDto.getBasketList().add(basketDto);
        productService.save(productDto);
        return "redirect:/product";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(Model model, EntityNotFoundException e) {
        model.addAttribute("message", e.getMessage());
        return "not_found";
    }
}
