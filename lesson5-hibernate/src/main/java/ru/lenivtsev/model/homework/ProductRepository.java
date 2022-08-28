package ru.lenivtsev.model.homework;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.Setter;
import ru.lenivtsev.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Setter
public class ProductRepository {

    private final EntityManagerFactory emFactory;

    public ProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<Product> findAll(){
        return executeEntityManager(entityManager ->
                entityManager.createNamedQuery("findAllProducts", Product.class).getResultList());

    }

    public long count(){
        return executeEntityManager(entityManager ->
                entityManager.createQuery("countAllProducts", Long.class).getSingleResult());
    }

    public Optional<Product> findById(long id) {
        return executeEntityManager(entityManager ->
                Optional.ofNullable(entityManager.find(Product.class, id)));
    }

    public void deleteById(long id) {
        executeInTransaction(entityManager ->
                entityManager.createNamedQuery("deleteProductById")
                        .setParameter("id", id)
                        .executeUpdate());
    }

    public void saveOrUpdate(Product product) {
        executeInTransaction(entityManager -> {
                    if (product.getId() == null) {
                        entityManager.persist(product);
                    } else {
                        entityManager.merge(product);
                    }
                });
    }

    private <R> R executeEntityManager(Function<EntityManager, R> function){
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            em.close();
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
