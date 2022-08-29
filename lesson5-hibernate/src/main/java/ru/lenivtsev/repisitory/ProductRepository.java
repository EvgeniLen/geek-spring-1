package ru.lenivtsev.repisitory;

import lombok.Setter;
import org.springframework.stereotype.Repository;
import ru.lenivtsev.CreationFactory;
import ru.lenivtsev.model.Customer;
import ru.lenivtsev.model.Product;
import java.util.List;
import java.util.Optional;

@Setter
@Repository
public class ProductRepository {

    private final CreationFactory creationFactory;

    public ProductRepository(CreationFactory creationFactory) {
        this.creationFactory = creationFactory;
    }

    public List<Product> findAll(){
        return creationFactory.executeEntityManager(entityManager ->
                entityManager.createNamedQuery("findAllProducts", Product.class).getResultList());

    }

    public long count(){
        return creationFactory.executeEntityManager(entityManager ->
                entityManager.createNamedQuery("countAllProducts", Long.class).getSingleResult());
    }

    public Optional<Product> findById(long id) {
        return creationFactory.executeEntityManager(entityManager ->
                Optional.ofNullable(entityManager.find(Product.class, id)));
    }

    public void deleteById(long id) {
        creationFactory.executeInTransaction(entityManager ->
                entityManager.createNamedQuery("deleteProductByID")
                        .setParameter("id", id)
                        .executeUpdate());
    }

    public void saveOrUpdate(Product product) {
        creationFactory.executeInTransaction(entityManager -> {
                    if (product.getId() == null) {
                        entityManager.persist(product);
                    } else {
                        entityManager.merge(product);
                    }
                });
    }
}
