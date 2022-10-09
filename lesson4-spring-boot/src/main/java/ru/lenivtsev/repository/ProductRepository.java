package ru.lenivtsev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lenivtsev.model.Product;

import java.math.BigDecimal;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            select * from products p 
            where (:productTitleFilter is null or p.title like :productTitleFilter)
            and ((:costFilterMin is null or :costFilterMax is null) or p.cost between :costFilterMin and :costFilterMax)
            and (:costFilterMax is null or p.cost < :costFilterMax)
            and (:costFilterMin is null or p.cost > :costFilterMin)
            """,
            countQuery = """
            select count(*) from products p 
            where (:productTitleFilter is null or p.title like :productTitleFilter)
            and ((:costFilterMin is null or :costFilterMax is null) or p.cost between :costFilterMin and :costFilterMax)
            and (:costFilterMax is null or p.cost < :costFilterMax)
            and (:costFilterMin is null or p.cost > :costFilterMin)
            """ ,
            nativeQuery = true)
    Page<Product> productByFilter(String productTitleFilter, BigDecimal costFilterMin, BigDecimal costFilterMax, Pageable pageable);

}