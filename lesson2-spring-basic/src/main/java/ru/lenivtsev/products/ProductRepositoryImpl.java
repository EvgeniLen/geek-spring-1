package ru.lenivtsev.products;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository(value = "productRepository")
public class ProductRepositoryImpl implements ProductRepository{

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    public void insert(Product product){
        long id = identity.incrementAndGet();
        product.setId(id);
        productMap.put(id, product);
    }

    public List<Product> findAll(){
        return new ArrayList<>(productMap.values());
    }

    public Product getProduct(long id) {
        return productMap.get(id);
    }
}
