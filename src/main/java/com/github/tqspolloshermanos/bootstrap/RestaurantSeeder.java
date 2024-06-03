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
        new Restaurant("Chipotle Mexican Grill", "321 Maple St", ECuisineType.MEXICAN, "Tasty Mexican tacos and more", 0),
        new Restaurant("Le Parisien", "123 Main St", ECuisineType.FRENCH, "Classic French cuisine with a modern twist", 0),
        new Restaurant("Mamma Mia Pizzeria", "567 Pine St", ECuisineType.ITALIAN, "Traditional Italian pizzas and pasta", 0),
        new Restaurant("Golden Dragon", "890 Walnut St", ECuisineType.CHINESE, "Authentic Chinese cuisine", 0));

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

        List<Product> products = new ArrayList<>();

        // Products for La Moderna
        products.add(new Product("Spaghetti Carbonara", persistedRestaurants.get(0), "Classic Italian pasta with bacon, eggs, and Parmesan cheese", 800));
        products.add(new Product("Margherita Pizza", persistedRestaurants.get(0), "Traditional Italian pizza with tomato, mozzarella, and basil", 900));
        products.add(new Product("Tiramisu", persistedRestaurants.get(0), "Classic Italian dessert made with coffee-soaked ladyfingers and mascarpone cheese", 600));
        products.add(new Product("Lasagna", persistedRestaurants.get(0), "Layers of pasta, meat, sauce, and cheese baked to perfection", 1000));

        // Products for Tokugawa Sushi
        products.add(new Product("California Roll", persistedRestaurants.get(1), "Sushi roll with crab meat, avocado, cucumber, and tobiko", 700));
        products.add(new Product("Tempura Shrimp Roll", persistedRestaurants.get(1), "Sushi roll with tempura shrimp, avocado, and cucumber", 850));
        products.add(new Product("Sashimi Platter", persistedRestaurants.get(1), "Assorted slices of fresh raw fish", 1200));
        products.add(new Product("Dragon Roll", persistedRestaurants.get(1), "Sushi roll with eel, avocado, and cucumber topped with sliced avocado and eel sauce", 1100));

        // Products for Chipotle Mexican Grill
        products.add(new Product("Burrito Bowl", persistedRestaurants.get(2), "Mexican dish consisting of rice, beans, meat, salsa, cheese, and sour cream", 950));
        products.add(new Product("Quesadilla", persistedRestaurants.get(2), "Flour tortilla filled with cheese and optionally other ingredients, then folded in half to form a half-moon shape", 800));
        products.add(new Product("Guacamole", persistedRestaurants.get(2), "Dip made from mashed avocado, lime juice, salt, and sometimes additional ingredients such as onions, tomatoes, and cilantro", 600));
        products.add(new Product("Chips and Salsa", persistedRestaurants.get(2), "Crispy corn tortilla chips served with salsa", 500));

        // Products for Le Parisien
        products.add(new Product("Beef Bourguignon", persistedRestaurants.get(3), "Classic French beef stew cooked with red wine, carrots, onions, and mushrooms", 1200));
        products.add(new Product("Croissant", persistedRestaurants.get(3), "Buttery, flaky pastry often enjoyed at breakfast or as a snack", 500));
        products.add(new Product("Ratatouille", persistedRestaurants.get(3), "Vegetable stew made with tomatoes, eggplant, zucchini, bell peppers, onions, and garlic", 900));
        products.add(new Product("Creme Brulee", persistedRestaurants.get(3), "Classic French dessert consisting of rich custard topped with a layer of caramelized sugar", 700));

        // Products for Mamma Mia Pizzeria
        products.add(new Product("Calzone", persistedRestaurants.get(4), "Folded pizza dough filled with cheese, sauce, and various toppings, then baked", 1000));
        products.add(new Product("Penne Arrabbiata", persistedRestaurants.get(4), "Pasta tossed in a spicy tomato sauce with garlic and red pepper flakes", 800));
        products.add(new Product("Caprese Salad", persistedRestaurants.get(4), "Simple Italian salad made with fresh tomatoes, mozzarella cheese, basil, olive oil, and balsamic glaze", 600));
        products.add(new Product("Garlic Bread", persistedRestaurants.get(4), "Toasted bread rubbed with garlic and topped with olive oil or butter", 400));

        // Products for Golden Dragon
        products.add(new Product("Kung Pao Chicken", persistedRestaurants.get(5), "Stir-fried Chinese dish made with chicken, peanuts, vegetables, and chili peppers", 1100));
        products.add(new Product("Spring Rolls", persistedRestaurants.get(5), "Chinese appetizer consisting of rolled-up pancakes filled with vegetables, meat, or seafood", 700));
        products.add(new Product("Mongolian Beef", persistedRestaurants.get(5), "Stir-fried beef dish cooked with onions and scallions in a savory sauce", 1300));
        products.add(new Product("Hot and Sour Soup", persistedRestaurants.get(5), "Spicy and tangy Chinese soup made with mushrooms, tofu, bamboo shoots, and eggs", 600));


        products.forEach(product -> {
            Optional<Product> optionalProduct = productRepository.findByName(product.getName());

            optionalProduct.ifPresentOrElse(System.out::println, () ->
                productRepository.save(product)
            );
        });
    }
}