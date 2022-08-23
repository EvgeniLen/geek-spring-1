package ru.lenivtsev.model.homework;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.Setter;
import ru.lenivtsev.model.Product;
import java.util.List;

@Setter
public class ProductRepository {

    private EntityManager entityManager;

    public List<Product> findAll(){
        return entityManager.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();

    }

    public Product findById(long id) {
        return entityManager.find(Product.class, id);
    }

    public void deleteById(long id) {
        Product product = findById(id);
        if (product != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(product);
            entityManager.getTransaction().commit();
        }
    }

    public Product saveOrUpdate(Product product) {
        entityManager.getTransaction().begin();
        if (product.getId() == null) {
            try {
                entityManager.merge(product);
            } catch (PersistenceException e){
                System.out.println("Product already exists");
            }
        } else {
            Product productInDb = findById(product.getId());
            productInDb.setTitle(product.getTitle());
            productInDb.setPrice(product.getPrice());
        }
        entityManager.getTransaction().commit();
        return product;
    }
}
