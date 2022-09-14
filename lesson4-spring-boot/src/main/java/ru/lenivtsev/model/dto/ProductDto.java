package ru.lenivtsev.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    @NotBlank(message = "Can not be empty!")
    private String title;

    @DecimalMax(message = "Cost can not exceed 1000000",
            value = "1000000")
    @DecimalMin(message = "Cost should be positive", value = "0")
    private BigDecimal cost;

    public ProductDto(BigDecimal cost) {
        this.cost = cost;
    }
}
