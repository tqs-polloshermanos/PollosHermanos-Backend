package com.github.tqspolloshermanos.backend;

import com.github.tqspolloshermanos.backend.Repositories.UserRepository;
import com.github.tqspolloshermanos.backend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class AppConfig {

    @Bean
    public IngredientService ingredientService() {
        return new IngredientService();
    };

    @Bean
    public UserService userService() {
        return new UserService();
    };

    @Bean
    public OrderService orderService() {
        return new OrderService();
    };

    @Bean
    public ProductIngredientsService productIngredientsService() {
        return new ProductIngredientsService();
    };

    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    };

    @Bean
    public ProductService productService() {
        return new ProductService();
    };

    @Bean
    public RestaurantService restaurantService() {
        return new RestaurantService();
    };

    @Bean
    public OrderDetailService orderDetailService() {
        return new OrderDetailService();
    };
}
