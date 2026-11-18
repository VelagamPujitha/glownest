package com.glownest.service;

import com.glownest.entity.Product;
import com.glownest.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getCategoryProducts(
            String category) {

        return repository.findByCategory(category);
    }
}
