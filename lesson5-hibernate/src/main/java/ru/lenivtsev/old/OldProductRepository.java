package ru.lenivtsev.old;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.Setter;
import ru.lenivtsev.model.Product;
import java.util.List;

@Setter
public class OldProductRepository {

    private EntityManager entityManager;

    public List<Product> findAll(){
        return entityManager.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();

    }

    public Product findById(long id) {
        return entityManager.find(Product.class, id);
    }

    public void deleteById(long id) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Product p WHERE p.id = :id", Product.class);
        entityManager.getTransaction().commit();
    }

    public Product saveOrUpdate(Product product) {
        entityManager.getTransaction().begin();
        if (product.getId() == null) {
            try {
                entityManager.persist(product);
            } catch (PersistenceException e){
                System.out.println("Product already exists");
            }
        } else {
            entityManager.merge(product);
        }
        entityManager.getTransaction().commit();
        return product;
    }
}
