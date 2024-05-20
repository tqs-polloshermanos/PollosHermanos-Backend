package com.github.tqspolloshermanos.backend.Services;

import com.github.tqspolloshermanos.backend.Entities.ECuisineType;
import com.github.tqspolloshermanos.backend.Entities.Product;
import com.github.tqspolloshermanos.backend.Repositories.ProductRepository;
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

    public List<Product> findProductsByRestaurant(Long restaurantId) {
        return productRepository.findByRestaurantId(restaurantId);
    }

    public List<Product> findProductsByCuisineType(ECuisineType cuisineType) {
        return productRepository.findByCuisineType(cuisineType);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
