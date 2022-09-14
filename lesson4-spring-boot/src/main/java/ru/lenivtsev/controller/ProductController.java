package ru.lenivtsev.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lenivtsev.exceptions.EntityNotFoundException;
import ru.lenivtsev.model.dto.ProductDto;
import ru.lenivtsev.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
        private final ProductService service;

        @GetMapping
        public String listPage(
                @RequestParam(required = false) String productTitleFilter,
                @RequestParam(required = false) String productCostFilter,
                @RequestParam(required = false) Optional<Integer> page,
                @RequestParam(required = false) Optional<Integer> size,
                @RequestParam(required = false) Optional<String> sortField,
                Model model) {
            int pageValue = page.orElse(1) - 1;
            if (pageValue < 0) pageValue = 0;
            int sizeValue = size.orElse((10));
            String sortFiledValue = sortField.filter(s -> !s.isBlank()).orElse("id");
            model.addAttribute("products", service.findAllByFilter(productTitleFilter, productCostFilter, pageValue, sizeValue, sortFiledValue));
            return "product";
        }

        @GetMapping("/{id}")
        public String form(@PathVariable("id") long id, Model model) {
            model.addAttribute("product", service.findByProductId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found")));
            return "product_form";
        }

        @GetMapping("/new")
        public String addNewProduct(Model model) {
            model.addAttribute("product", new ProductDto(new BigDecimal(BigInteger.ZERO)));
            return "product_form";
        }

        @DeleteMapping("{id}")
        public String deleteProductById(@PathVariable long id) {
            service.deleteProductById(id);
            return "redirect:/product";
        }

        @PostMapping
        public String saveProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "product_form";
            }
            service.save(product);
            return "redirect:/product";
        }

        @PostMapping("/update")
        public String updateProduct(@ModelAttribute("product") ProductDto product) {
            service.save(product);
            return "redirect:/product";
        }

        @ExceptionHandler
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public String notFoundExceptionHandler(Model model, EntityNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "not_found";
        }
}
