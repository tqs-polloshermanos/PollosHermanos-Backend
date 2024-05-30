package com.github.tqspolloshermanos.repositories;

import com.github.tqspolloshermanos.entities.Product;
import com.github.tqspolloshermanos.entities.Restaurant;
import com.github.tqspolloshermanos.entities.ECuisineType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setCuisineType(ECuisineType.ITALIAN);
        restaurant.setDescription("A test restaurant");
        restaurant.setNumberOfOrders(0);

        restaurant = restaurantRepository.save(restaurant);

        Product product1 = new Product("Pizza Royal", restaurant, "Delicious pizza with pepperoni", 1000);
        Product product2 = new Product("Burger Deluxe", restaurant, "Juicy burger with cheese and bacon", 1200);

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    public void testFindByRestaurantId() {
        List<Product> products = productRepository.findByRestaurantId(restaurant.getId());
        assertEquals(2, products.size());
    }

    @Test
    public void testFindByName() {
        Optional<Product> product = productRepository.findByName("Pizza Royal");
        assertTrue(product.isPresent());
        assertEquals("Pizza Royal", product.get().getName());
    }

    @Test
    public void testFindByNameNotFound() {
        Optional<Product> product = productRepository.findByName("Non-Existent Product");
        assertFalse(product.isPresent());
    }
}
