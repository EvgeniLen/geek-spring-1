package ru.lenivtsev;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;


import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class CreationFactory {
    private final EntityManagerFactory emFactory;
    private static CreationFactory creationFactory;

    public static synchronized CreationFactory getFactory(){
        if (creationFactory == null){
            creationFactory = new CreationFactory();
        }
        return creationFactory;
    }

    private CreationFactory() {
        emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public void close(){
        emFactory.close();
    }

    public  <R> R executeEntityManager(Function<EntityManager, R> function){
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            em.close();
        }
    }

    public void executeInTransaction(Consumer<EntityManager> consumer) {
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
