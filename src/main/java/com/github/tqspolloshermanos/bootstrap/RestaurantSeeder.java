package com.github.tqspolloshermanos.bootstrap;

import com.github.tqspolloshermanos.entities.Restaurant;
import com.github.tqspolloshermanos.entities.ECuisineType;
import com.github.tqspolloshermanos.repositories.RestaurantRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RestaurantSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RestaurantRepository restaurantRepository;

    public RestaurantSeeder(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRestaurants();
    }

    private void loadRestaurants() {
        List<Restaurant> restaurants = List.of(
        new Restaurant("La Moderna", "456 Elm St", ECuisineType.ITALIAN, "Authentic Italian pasta dishes", 0),
        new Restaurant("Tokugawa Sushi", "789 Oak St", ECuisineType.JAPANESE, "Fresh and delicious sushi", 0),
        new Restaurant("Chipotle Mexican Grill", "321 Maple St", ECuisineType.MEXICAN, "Tasty Mexican tacos and more", 0));

        restaurants.forEach(restaurant -> {
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findByName(restaurant.getName());

            optionalRestaurant.ifPresentOrElse(System.out::println, () -> {
                restaurantRepository.save(restaurant);
            });
        });
    }
}