package com.glownest.controller;

import com.glownest.entity.Product;
import com.glownest.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getProducts() {
        return service.getAllProducts();
    }

    @PostMapping
    public Product createProduct(
            @RequestBody Product product) {

        return service.addProduct(product);
    }

    @GetMapping("/category/{category}")
    public List<Product> categoryProducts(
            @PathVariable String category) {

        return service.getCategoryProducts(category);
    }
}
