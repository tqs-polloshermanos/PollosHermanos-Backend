package com.github.tqspolloshermanos.backend.ControllersTests;

import com.github.tqspolloshermanos.backend.Entities.ECuisineType;
import com.github.tqspolloshermanos.backend.Entities.Restaurant;
import com.github.tqspolloshermanos.backend.Services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void testGetAllRestaurants() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Restaurant 1", "Address 1", ECuisineType.MEXICAN, "Description 1", "image1.jpg", 10));
        restaurants.add(new Restaurant("Restaurant 2", "Address 2", ECuisineType.ITALIAN, "Description 2", "image2.jpg", 20));

        Mockito.when(restaurantService.findAllRestaurants()).thenReturn(restaurants);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetRestaurantById() throws Exception {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant("Restaurant 1", "Address 1", ECuisineType.MEXICAN, "Description 1", "image1.jpg", 10);

        Mockito.when(restaurantService.findRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/{id}", restaurantId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetRestaurantsByCuisineType() throws Exception {
        ECuisineType cuisineType = ECuisineType.MEXICAN;
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Restaurant 1", "Address 1", ECuisineType.MEXICAN, "Description 1", "image1.jpg", 10));
        restaurants.add(new Restaurant("Restaurant 2", "Address 2", ECuisineType.MEXICAN, "Description 2", "image2.jpg", 20));

        Mockito.when(restaurantService.findRestaurantsByCuisineType(cuisineType)).thenReturn(restaurants);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/cuisine/{cuisineType}", cuisineType))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
