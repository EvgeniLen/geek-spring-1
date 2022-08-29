package ru.lenivtsev.repisitory;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.lenivtsev.CreationFactory;
import ru.lenivtsev.model.Customer;
import ru.lenivtsev.model.Product;

import java.util.List;
import java.util.Optional;

@Setter
@Repository
public class CustomerRepository {

    private final CreationFactory creationFactory;


    public CustomerRepository(CreationFactory creationFactory) {
        this.creationFactory = creationFactory;
    }

    public List<Customer> findAll(){
        return creationFactory.executeEntityManager(entityManager ->
                entityManager.createNamedQuery("findAllCustomer", Customer.class).getResultList());

    }

    public long count(){
        return creationFactory.executeEntityManager(entityManager ->
                entityManager.createNamedQuery("countAllCustomer", Long.class).getSingleResult());
    }

    public Optional<Customer> findById(long id) {
        return creationFactory.executeEntityManager(entityManager ->
                Optional.ofNullable(entityManager.find(Customer.class, id)));
    }

    public void deleteById(long id) {
        creationFactory.executeInTransaction(entityManager ->
                entityManager.createNamedQuery("deleteCustomerByID")
                        .setParameter("id", id)
                        .executeUpdate());
    }

    public void saveOrUpdate(Customer customer) {
        creationFactory.executeInTransaction(entityManager -> {
                    if (customer.getId() == null) {
                        entityManager.persist(customer);
                    } else {
                        entityManager.merge(customer);
                    }
                });
    }
}
