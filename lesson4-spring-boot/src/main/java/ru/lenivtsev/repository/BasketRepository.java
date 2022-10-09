package ru.lenivtsev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lenivtsev.model.Basket;
import ru.lenivtsev.model.User;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query(value = """
            select * from baskets b
            where (b.owner_id = :id)
            """,
            nativeQuery = true)
    Basket findBasketByOwnerId(Long id);
    //public Basket findBasketByOwner(User owner);
}
