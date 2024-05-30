package com.github.tqspolloshermanos.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductConstructorAndGetters() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        Product product = new Product("Pizza Royal", restaurant, "Delicious pizza with pepperoni", 1000);

        assertNull(product.getId()); // ID should be null before being persisted
        assertEquals("Pizza Royal", product.getName());
        assertEquals(restaurant, product.getRestaurant());
        assertEquals("Delicious pizza with pepperoni", product.getDescription());
        assertEquals(1000, product.getPrice());
    }

    @Test
    void testProductSetters() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        Product product = new Product();
        product.setId(1L);
        product.setName("Burger Deluxe");
        product.setRestaurant(restaurant);
        product.setDescription("Juicy burger with cheese and bacon");
        product.setPrice(1200);

        assertEquals(1L, product.getId());
        assertEquals("Burger Deluxe", product.getName());
        assertEquals(restaurant, product.getRestaurant());
        assertEquals("Juicy burger with cheese and bacon", product.getDescription());
        assertEquals(1200, product.getPrice());
    }
}
