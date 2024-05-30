package com.github.tqspolloshermanos.bootstrap;

import com.github.tqspolloshermanos.entities.Product;
import com.github.tqspolloshermanos.entities.Restaurant;
import com.github.tqspolloshermanos.entities.ECuisineType;
import com.github.tqspolloshermanos.repositories.ProductRepository;
import com.github.tqspolloshermanos.repositories.RestaurantRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RestaurantSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;

    public RestaurantSeeder(RestaurantRepository restaurantRepository, ProductRepository productRepository) {
        this.restaurantRepository = restaurantRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRestaurants();
        this.loadProducts();
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

    private void loadProducts() {
        List<Restaurant> persistedRestaurants = restaurantRepository.findAll(); // Ensure all restaurants are fetched from the database

        if (persistedRestaurants.size() < 3) {
            return; // Exit if restaurants are not properly loaded
        }

        List<Product> products = List.of(
                new Product("Pizza Royal", persistedRestaurants.get(0), "45 cm radius traditional italian pizza with cheese, pepperoni, olives and rucola", 1000),
                new Product("10 Mako Rolls", persistedRestaurants.get(1), "Ten mako sushi rolls with salmon, surimi and cucumber", 750),
                new Product("Taco", persistedRestaurants.get(2), "Traditional mexican taco filled with cheese, ground pork, lettuce and tomatoes", 700)
        );

        products.forEach(product -> {
            Optional<Product> optionalProduct = productRepository.findByName(product.getName());

            optionalProduct.ifPresentOrElse(System.out::println, () -> {
                productRepository.save(product);
            });
        });
    }
}