package ru.lenivtsev.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lenivtsev.exceptions.EntityNotFoundException;
import ru.lenivtsev.products.Product;
import ru.lenivtsev.products.ProductRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
        private static final Pattern PARAM_PATTERN_LESS = Pattern.compile("\\<(\\d+)");
        private static final Pattern PARAM_PATTERN_MORE= Pattern.compile("\\>(\\d+)");
        private static final Pattern PARAM_PATTERN_BETWEEN = Pattern.compile("(\\d+)\\-(\\d+)");

        private final ProductRepository productRepository;

        @GetMapping
        public String listPage(@RequestParam Optional<String> productFilter, Model model) {
            if (productFilter.isEmpty() || productFilter.get().isBlank()){
                model.addAttribute("products", productRepository.findAll());

            } else {
                Matcher matcher = PARAM_PATTERN_BETWEEN.matcher(productFilter.get());
                if (matcher.matches()) {
                    model.addAttribute("products", productRepository.getProductsByCostBetween(new BigDecimal(matcher.group(1)), new BigDecimal(matcher.group(2))));
                }
                matcher = PARAM_PATTERN_LESS.matcher(productFilter.get());
                if (matcher.matches()) {
                    model.addAttribute("products", productRepository.getProductsByCostBefore(new BigDecimal(matcher.group(1))));
                }
                matcher = PARAM_PATTERN_MORE.matcher(productFilter.get());
                if (matcher.matches()) {
                    model.addAttribute("products", productRepository.getProductsByCostAfter(new BigDecimal(matcher.group(1))));
                }
            }
            return "product";
        }

        @GetMapping("/{id}")
        public String form(@PathVariable("id") long id, Model model) {
            model.addAttribute("product", productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found")));
            return "product_form";
        }

        @GetMapping("/new")
        public String addNewProduct(Model model) {
            model.addAttribute("product", new Product("", BigDecimal.ZERO));
            return "product_form";
        }

        @DeleteMapping("{id}")
        public String deleteProductById(@PathVariable long id) {
            productRepository.deleteById(id);
            return "redirect:/product";
        }

        @PostMapping
        public String saveProduct(@Valid Product product, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "product_form";
            }
            productRepository.save(product);
            return "redirect:/product";
        }

        @PostMapping("/update")
        public String updateProduct(Product product) {
            productRepository.save(product);
            return "redirect:/product";
        }

        @ExceptionHandler
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public String notFoundExceptionHandler(Model model, EntityNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "not_found";
        }
}
