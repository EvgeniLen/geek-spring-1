package ru.lenivtsev.model;

import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> findAll(){
        return entityManager.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();

    }

    public Optional<Product> findById(long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    public void deleteById(long id) {
        Product product = entityManager.find(Product.class, id);
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
            Product productInDb = entityManager.find(Product.class, product.getId());
            productInDb.setTitle(product.getTitle());
            productInDb.setPrice(product.getPrice());
        }
        entityManager.getTransaction().commit();
        return product;
    }
}
