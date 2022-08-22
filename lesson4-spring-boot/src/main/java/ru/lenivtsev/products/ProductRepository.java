package ru.lenivtsev.products;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository{

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init(){
        this.insert(new Product("Potato", 5L));
        this.insert(new Product("Tomato", 23L));
        this.insert(new Product("Apple", 20L));
        this.insert(new Product("Bananas", 10L));
        this.insert(new Product("Carrot", 8L));
    }

    public void insert(Product product){
        long id = identity.incrementAndGet();
        product.setId(id);
        productMap.put(id, product);
    }

    public List<Product> findAll(){
        return new ArrayList<>(productMap.values());
    }

    public Optional<Product> findById(long id) {

        return Optional.ofNullable(productMap.get(id));
    }

    public void delete(long id) {
        productMap.remove(id);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(identity.incrementAndGet());
        }
        productMap.put(product.getId(), product);
        return product;
    }
}