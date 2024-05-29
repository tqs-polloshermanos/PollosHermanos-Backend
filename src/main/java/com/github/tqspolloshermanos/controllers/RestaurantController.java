package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.entities.Restaurant;
import com.github.tqspolloshermanos.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
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

    @GetMapping("/image/{id}")
    public ResponseEntity<String> getRestaurantImageById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.findRestaurantById(id);
        if (!restaurant.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }

        byte[] image = restaurant.get().getImage();
        if (image == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No image found for the restaurant");
        }

        String base64Image = Base64.getEncoder().encodeToString(image);
        return ResponseEntity.ok(base64Image);
    }
}
