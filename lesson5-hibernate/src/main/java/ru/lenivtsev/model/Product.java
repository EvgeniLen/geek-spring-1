package ru.lenivtsev.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "findAllProducts", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "countAllProducts", query = "SELECT count(p) FROM Product p"),
        @NamedQuery(name = "deleteProductByID", query = "DELETE FROM Product p WHERE p.id = :id")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false)
    private Long price;

    public Product(String title, Long price) {
        this.title = title;
        this.price = price;
    }
}
