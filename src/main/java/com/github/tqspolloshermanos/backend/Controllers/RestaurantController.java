package com.github.tqspolloshermanos.backend.Controllers;

import com.github.tqspolloshermanos.backend.Entities.ECuisineType;
import com.github.tqspolloshermanos.backend.Entities.Restaurant;
import com.github.tqspolloshermanos.backend.Services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.findRestaurantById(id);
        return restaurant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cuisine/{cuisineType}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisineType(@PathVariable ECuisineType cuisineType) {
        List<Restaurant> restaurants = restaurantService.findRestaurantsByCuisineType(cuisineType);
        return ResponseEntity.ok(restaurants);
    }
}
