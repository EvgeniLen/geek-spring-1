package ru.lenivtsev.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "customers")
@NamedQueries({
        @NamedQuery(name = "findAllCustomer", query = "SELECT c FROM Customer c"),
        @NamedQuery(name = "countAllCustomer", query = "SELECT count(c) FROM Customer c"),
        @NamedQuery(name = "deleteCustomerByID", query = "DELETE FROM Customer c WHERE c.id = :id")
})
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.EAGER)
    @JoinTable(name = "customer_products",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;

    public Customer(String username, List<Product> productList) {
        this.name = username;
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
