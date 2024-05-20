package com.github.tqspolloshermanos.backend.ServicesTests;

import com.github.tqspolloshermanos.backend.Entities.ECuisineType;
import com.github.tqspolloshermanos.backend.Entities.Product;
import com.github.tqspolloshermanos.backend.Repositories.ProductRepository;
import com.github.tqspolloshermanos.backend.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Test
    void testFindProductsByCuisineType() {
        // Mock repository response
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Product 1", ECuisineType.MEXICAN, null, "Description 1", 10.0, "image1.jpg"));
        products.add(new Product("Product 2", ECuisineType.MEXICAN, null, "Description 2", 12.0, "image2.jpg"));
        when(productRepository.findByCuisineType(ECuisineType.MEXICAN)).thenReturn(products);

        // Test service method
        List<Product> result = productService.findProductsByCuisineType(ECuisineType.MEXICAN);
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
    }

}
