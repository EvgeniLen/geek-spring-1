package ru.lenivtsev.products;

import java.security.PublicKey;
import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product getProduct(long id);

    void insert(Product product);

}
