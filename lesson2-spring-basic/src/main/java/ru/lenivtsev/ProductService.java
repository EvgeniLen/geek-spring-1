package ru.lenivtsev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lenivtsev.repository.Product;
import ru.lenivtsev.repository.ProductRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init(){
        this.productRepository.insert(new Product("Potato", 5));
        this.productRepository.insert(new Product("Tomato", 23));
        this.productRepository.insert(new Product("Apple", 20));
        this.productRepository.insert(new Product("Bananas", 10));
        this.productRepository.insert(new Product("Carrot", 8));
    }

    public List<Product> findAll(){
        return this.productRepository.findAll();
    }

    public Product getProduct(long id) {
        return this.productRepository.getProduct(id);
    }

}
