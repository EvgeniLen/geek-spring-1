package ru.lenivtsev.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lenivtsev.products.Product;
import ru.lenivtsev.products.ProductRepository;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

        private final ProductRepository productRepository;

        @GetMapping
        public String listPage(Model model) {
            model.addAttribute("products", productRepository.findAll());
            return "product";
        }

        @GetMapping("/{id}")
        public String form(@PathVariable("id") long id, Model model) {
            Optional<Product> optionalProduct = productRepository.findById(id);
            model.addAttribute("product", productRepository.findById(id));
            return "product_form";
        }

        @GetMapping("/new")
        public String addNewProduct(Model model) {
            model.addAttribute("product", new Product("", 0L));
            return "product_form";
        }

        @DeleteMapping("/delete/{id}")
        public String deleteUserById(@PathVariable long id) {
            productRepository.delete(id);
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
}
