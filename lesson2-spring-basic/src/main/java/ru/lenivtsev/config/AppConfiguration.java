package ru.lenivtsev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.lenivtsev.Cart;
import ru.lenivtsev.ProductService;
import ru.lenivtsev.products.ProductRepository;
import ru.lenivtsev.products.ProductRepositoryImpl;

@Configuration
public class AppConfiguration {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }

    @Bean
    @Scope("prototype")
    public Cart cart(){
        return new Cart();
    }
}
