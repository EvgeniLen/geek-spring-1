package ru.lenivtsev;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {
    private final List<Long> listProduct;

    public Cart() {
        listProduct = new ArrayList<>();
    }

    public void addToCart(long id){
        listProduct.add(id);
    }

    public void removeFromCart(long id){
        listProduct.remove(id);
    }

    public List<Long> getListProduct() {
        return listProduct;
    }
}
