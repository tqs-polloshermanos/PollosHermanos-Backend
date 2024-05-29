package com.github.tqspolloshermanos.entities;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantTest {

    @Test
    void testRestaurantEntityMethods() {
        // Create a Restaurant object
        Restaurant restaurant = new Restaurant();

        // Set values
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test Street");
        restaurant.setCuisineType(ECuisineType.ITALIAN);
        restaurant.setDescription("A lovely Italian restaurant");
        byte[] image = Base64.getDecoder().decode("aGVsbG93b3JsZA==");  // Example base64 encoded image
        restaurant.setNumberOfOrders(100);

        // Test getters
        assertEquals(1L, restaurant.getId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("123 Test Street", restaurant.getAddress());
        assertEquals(ECuisineType.ITALIAN, restaurant.getCuisineType());
        assertEquals("A lovely Italian restaurant", restaurant.getDescription());
        assertEquals(100, restaurant.getNumberOfOrders());
    }
}
