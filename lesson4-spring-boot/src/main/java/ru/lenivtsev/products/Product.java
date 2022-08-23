package ru.lenivtsev.products;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class Product {
    private Long id;
    @NotBlank(message = "Can not be empty!")
    private String name;
    @DecimalMax(message = "Cost can not exceed 999",
            value = "999")
    @DecimalMin(message = "Cost should be positive", value = "0")
    private Long cost;

    @DecimalMax(message = "Min cost can not exceed 999",
            value = "999")
    @DecimalMin(message = "Min cost should be positive", value = "0")
    private Long minCost;

    @DecimalMax(message = "Max cost can not exceed 999",
            value = "999")
    @DecimalMin(message = "Max cost should be positive", value = "0")
    private Long maxCost;

    @NotBlank(message = "Can not be empty!")
    //Проверка даты в html уже втроенная
    private String date;

    public Product(String name, Long cost) {
        this.name = name;
        this.cost = cost;
    }

//    public String getDate() {
//        if (date != null && !date.isEmpty()) {
//            String newDate;
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
//            Date oldDate = null;
//            try {
//                oldDate = simpleDateFormat.parse(date);
//                newDate = mySimpleDateFormat.format(oldDate);
//                return newDate;
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return date;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
