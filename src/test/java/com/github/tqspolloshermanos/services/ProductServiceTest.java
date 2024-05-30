package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.entities.Product;
import com.github.tqspolloshermanos.entities.Restaurant;
import com.github.tqspolloshermanos.entities.ECuisineType;
import com.github.tqspolloshermanos.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setCuisineType(ECuisineType.ITALIAN);
        restaurant.setDescription("A test restaurant");
        restaurant.setNumberOfOrders(0);

        product = new Product();
        product.setId(1L);
        product.setName("Pizza Royal");
        product.setRestaurant(restaurant);
        product.setDescription("Delicious pizza with pepperoni");
        product.setPrice(1000);
    }

    @Test
    void testFindById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals("Pizza Royal", foundProduct.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productService.findAll();

        assertEquals(1, foundProducts.size());
        assertEquals("Pizza Royal", foundProducts.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductsByRestaurantId() {
        List<Product> products = List.of(product);
        when(productRepository.findByRestaurantId(1L)).thenReturn(products);

        List<Product> foundProducts = productService.findProductsByRestaurantId(1L);

        assertEquals(1, foundProducts.size());
        assertEquals("Pizza Royal", foundProducts.get(0).getName());
        verify(productRepository, times(1)).findByRestaurantId(1L);
    }
}
