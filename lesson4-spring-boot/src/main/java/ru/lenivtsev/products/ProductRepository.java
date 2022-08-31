package ru.lenivtsev.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getProductsByCostAfter(BigDecimal costFilter);
    List<Product> getProductsByCostBefore(BigDecimal costFilter);
    List<Product> getProductsByCostBetween(BigDecimal costFilterMin, BigDecimal costFilterMax);

    @Query(value = """
        select * from products p 
        where (:costFilter is null or p.cost > :costfilter)
        """, nativeQuery = true)
    List<Product> productByCost(BigDecimal costFilter);

}