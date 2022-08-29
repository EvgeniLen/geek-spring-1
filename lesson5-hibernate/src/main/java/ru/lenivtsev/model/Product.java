package ru.lenivtsev.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
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
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Long price;

    public Product(String title, Long price) {
        this.title = title;
        this.price = price;
    }

    @ManyToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.EAGER)
    @JoinTable(name = "customer_products",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(title, product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", customers=" + customers.toString() +
                '}';
    }
}
