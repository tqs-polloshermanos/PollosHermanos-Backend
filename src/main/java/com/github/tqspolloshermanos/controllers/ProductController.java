package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.entities.Product;
import com.github.tqspolloshermanos.services.ProductService;
import com.github.tqspolloshermanos.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> new ProductDto(product))
                .collect(Collectors.toList());
        return productDtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProductDto productDto = new ProductDto(product.get());
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ProductDto>> getProductsByRestaurantId(@PathVariable Long restaurantId) {
        List<Product> products = productService.findProductsByRestaurantId(restaurantId);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ProductDto> productDtos = products.stream()
                .map(product -> new ProductDto(product))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtos);
    }
}
