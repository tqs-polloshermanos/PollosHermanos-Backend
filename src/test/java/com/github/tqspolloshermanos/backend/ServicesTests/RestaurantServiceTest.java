package com.github.tqspolloshermanos.backend.ServicesTests;

import com.github.tqspolloshermanos.backend.Entities.ECuisineType;
import com.github.tqspolloshermanos.backend.Entities.Restaurant;
import com.github.tqspolloshermanos.backend.Repositories.RestaurantRepository;
import com.github.tqspolloshermanos.backend.Services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        restaurantService = new RestaurantService(restaurantRepository);
    }

    @Test
    void testFindRestaurantsByCuisineType() {
        // Mock repository response
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Restaurant 1", "Address 1", ECuisineType.MEXICAN, "Description 1", "image1.jpg", 10));
        restaurants.add(new Restaurant("Restaurant 2", "Address 2", ECuisineType.MEXICAN, "Description 2", "image2.jpg", 20));
        when(restaurantRepository.findByCuisineType(ECuisineType.MEXICAN)).thenReturn(restaurants);

        // Test service method
        List<Restaurant> result = restaurantService.findRestaurantsByCuisineType(ECuisineType.MEXICAN);
        assertEquals(2, result.size());
        assertEquals("Restaurant 1", result.get(0).getName());
        assertEquals("Restaurant 2", result.get(1).getName());
    }
}
