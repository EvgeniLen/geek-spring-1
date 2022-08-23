package ru.lenivtsev;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import ru.lenivtsev.model.Product;
import ru.lenivtsev.model.homework.ProductRepository;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        /*entityManager.getTransaction().begin();
        entityManager.persist(new Product("Potato", 5L));
        entityManager.persist(new Product("Tomato", 23L));
        entityManager.persist(new Product("Apple", 20L));
        entityManager.persist(new Product("Bananas", 10L));
        entityManager.persist(new Product("Carrot", 8L));
        entityManager.getTransaction().commit();*/

        ProductRepository productRepository = new ProductRepository();
        productRepository.setEntityManager(entityManager);

        productRepository.findById(2L);
        productRepository.deleteById(3L);

        Product product = new Product("newProd3", 110L);
        productRepository.saveOrUpdate(product);

        product = new Product("newProd2", 110L);
        product.setId(1L);
        productRepository.saveOrUpdate(product);

        entityManager.close();
        entityManagerFactory.close();
    }
}
