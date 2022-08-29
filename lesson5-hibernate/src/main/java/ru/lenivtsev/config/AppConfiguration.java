package ru.lenivtsev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lenivtsev.CreationFactory;
import ru.lenivtsev.repisitory.CustomerRepository;
import ru.lenivtsev.repisitory.ProductRepository;
import ru.lenivtsev.service.GlobalService;


@Configuration
public class AppConfiguration {

    @Bean
    public CreationFactory creationFactory(){
        return CreationFactory.getFactory();
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository(creationFactory());
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepository(creationFactory());
    }

    @Bean
    public GlobalService globalService(ProductRepository productRepository, CustomerRepository customerRepository) {
        return new GlobalService(productRepository, customerRepository);
    }

}
