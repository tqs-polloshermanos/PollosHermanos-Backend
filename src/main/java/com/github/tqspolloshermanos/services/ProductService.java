package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.entities.ECuisineType;
import com.github.tqspolloshermanos.entities.Product;
import com.github.tqspolloshermanos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findProductsByRestaurantId(Long restaurantId) {
        return productRepository.findByRestaurantId(restaurantId);
    }
}
