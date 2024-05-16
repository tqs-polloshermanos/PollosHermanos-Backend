package com.github.tqspolloshermanos.backend.Services;

import com.github.tqspolloshermanos.backend.Entities.CuisineType;
import com.github.tqspolloshermanos.backend.Entities.Product;
import com.github.tqspolloshermanos.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getProductsByRestaurantId(Long restaurantId) {
        return productRepository.findByRestaurantId(restaurantId);
    }

    public List<Product> getProductsByCuisineType(CuisineType cuisineType) {
        return productRepository.findByRestaurantCuisineType(cuisineType);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }
}
