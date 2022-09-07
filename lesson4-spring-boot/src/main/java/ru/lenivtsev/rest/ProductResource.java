package ru.lenivtsev.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.lenivtsev.exceptions.EntityNotFoundException;
import ru.lenivtsev.model.dto.ProductDto;
import ru.lenivtsev.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService service;

    @GetMapping
    public List<ProductDto> listPage(
            @RequestParam(required = false) String productTitleFilter,
            @RequestParam(required = false) String productCostFilter,
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size,
            @RequestParam(required = false) Optional<String> sortField) {
        int pageValue = page.orElse(1) - 1;
        if (pageValue < 0) pageValue = 0;
        int sizeValue = size.orElse((10));
        String sortFiledValue = sortField.filter(s -> !s.isBlank()).orElse("id");
        Page<ProductDto> allByFilter = service.findAllByFilter(productTitleFilter, productCostFilter, pageValue, sizeValue, sortFiledValue);
        List<ProductDto> products = allByFilter.get().collect(Collectors.toList());
        return products;
    }

    @GetMapping("/{id}")
    public ProductDto form(@PathVariable("id") long id) {
        ProductDto product = service.findByProductId(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return product;
    }

    @PostMapping
    public ProductDto saveProduct(@RequestBody ProductDto product) {
        if (product.getId() != null) {
           throw new IllegalArgumentException("Created product shouldn't have id");
        }
        service.save(product);
        return product;
    }

    @DeleteMapping("{id}")
    public String deleteProductById(@PathVariable long id) {
        service.deleteProductById(id);
        return "complete";
    }

    @PostMapping("/update")
    public ProductDto updateProduct(@RequestBody ProductDto product) {
        service.save(product);
        return product;
    }
}
