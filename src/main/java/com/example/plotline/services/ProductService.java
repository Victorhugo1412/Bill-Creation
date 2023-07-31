package com.example.plotline.services;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.plotline.entity.Product;
import com.example.plotline.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
