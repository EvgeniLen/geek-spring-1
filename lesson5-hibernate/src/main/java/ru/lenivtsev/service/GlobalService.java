package ru.lenivtsev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lenivtsev.EntityNotFoundException;
import ru.lenivtsev.model.Customer;
import ru.lenivtsev.model.Product;
import ru.lenivtsev.repisitory.CustomerRepository;
import ru.lenivtsev.repisitory.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GlobalService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public GlobalService(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public List<Product> findProductByUserId(long userId){
        return customerRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"))
                .getProductList();
    }

    public List<Customer> findUserByProductId(long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"))
                .getCustomers();
    }

    public AtomicLong getPriceProducts(long userId){
        AtomicLong sum = new AtomicLong();
        findProductByUserId(userId).forEach(product -> sum.set(sum.get() + product.getPrice()));
        return sum;
    }
}
