package com.github.tqspolloshermanos.backend;

import com.github.tqspolloshermanos.backend.Entities.*;
import com.github.tqspolloshermanos.backend.Services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ServiceTest implements CommandLineRunner {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductIngredientsService productIngredientsService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OrderDetailService orderDetailService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Test IngredientService
        Ingredient ingredient1 = new Ingredient("Salt", "Common table salt");
        ingredientService.saveIngredient(ingredient1);

        Ingredient ingredient2 = new Ingredient("Pepper", "Black pepper");
        ingredientService.saveIngredient(ingredient2);

        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        System.out.println("All Ingredients:");
        for (Ingredient ingredient : ingredients) {
            System.out.println("Name: " + ingredient.getIngredientName() + ", Description: " + ingredient.getDescription());
        }

        // Test UserService
        User user1 = new User("user1@example.com", "password1");
        userService.saveUser(user1);

        Optional<User> user = userService.getUserById(1L);
        System.out.println("User with ID 1: " + user.map(u -> "Email: " + u.getEmail() + ", Password: " + u.getPassword()).orElse(null));

        // Test OrderService
        List<Order> orders = orderService.getAllOrders();
        System.out.println("All Orders:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId() + ", User: " + order.getUser().getEmail() + ", Restaurant: " + order.getRestaurant().getRestaurantName() + ", Order Date: " + order.getOrderDate());
        }

        // Test ProductIngredientsService
        List<ProductIngredients> productIngredients = productIngredientsService.getAllProductIngredients();
        System.out.println("All Product Ingredients:");
        for (ProductIngredients pi : productIngredients) {
            System.out.println("Product ID: " + pi.getProduct().getProductId() + ", Ingredient: " + pi.getIngredient().getIngredientName() + ", Quantity: " + pi.getQuantity());
        }

        // Test PaymentService
        List<Payment> payments = paymentService.getAllPayments();
        System.out.println("All Payments:");
        for (Payment payment : payments) {
            System.out.println("Order ID: " + payment.getOrder().getOrderId() + ", Amount: " + payment.getAmount() + ", Card Number: " + payment.getCardNumber());
        }

        // Test ProductService
        List<Product> products = productService.getAllProducts();
        System.out.println("All Products:");
        for (Product product : products) {
            System.out.println("Product ID: " + product.getProductId() + ", Name: " + product.getProductName() + ", Price: " + product.getPrice());
        }

        // Test RestaurantService
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        System.out.println("All Restaurants:");
        for (Restaurant restaurant : restaurants) {
            System.out.println("Restaurant ID: " + restaurant.getRestaurantId() + ", Name: " + restaurant.getRestaurantName() + ", Cuisine Type: " + restaurant.getCuisineType());
        }

        // Test OrderDetailService
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        System.out.println("All Order Details:");
        for (OrderDetail od : orderDetails) {
            System.out.println("Order Detail ID: " + od.getOrderDetailId() + ", Product: " + od.getProduct().getProductName() + ", Quantity: " + od.getQuantity());
        }
    }
}
