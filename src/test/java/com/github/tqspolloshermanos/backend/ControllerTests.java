package com.github.tqspolloshermanos.backend;

import com.github.tqspolloshermanos.backend.Entities.*;
import com.github.tqspolloshermanos.backend.Services.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ProductService productService;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private UserService userService;

    @Test
    void testGetAllIngredients() throws Exception {
        // Mock service response
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Salt", "Common table salt"));
        ingredients.add(new Ingredient("Sugar", "Granulated sugar"));

        when(ingredientService.findAll()).thenReturn(ingredients);

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetIngredientById() throws Exception {
        // Mock service response
        Ingredient ingredient = new Ingredient("Salt", "Common table salt");
        Long ingredientId = 1L;
        when(ingredientService.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ingredients/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetOrdersByUserId() throws Exception {
        // Mock service response
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        when(orderService.findOrdersByUserId(1L)).thenReturn(orders);

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    void testProcessPayment() throws Exception {
        // Mock service response
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        when(paymentService.processPayment(Mockito.any(Payment.class))).thenReturn(payment);

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\": 1, \"amount\": 100.0, \"cardNumber\": \"1234-5678-9012-3456\", \"cardHolderName\": \"John Doe\", \"cardExpiryDate\": \"2024-12-31\", \"cardCVV\": \"123\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testGetProductIngredients() throws Exception {
        // Mock service response
        Long productId = 1L;
        List<ProductIngredient> ingredients = new ArrayList<>();
        ingredients.add(new ProductIngredient());
        ingredients.add(new ProductIngredient());

        Product product = new Product();
        product.setId(productId);
        product.setProductIngredients(ingredients);

        when(productService.findById(productId)).thenReturn(Optional.of(product));

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductsByRestaurantId() throws Exception {
        // Mock service response
        List<Product> products = new ArrayList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L); // Set up a restaurant with ID 1
        products.add(new Product("Product 1", ECuisineType.MEXICAN, restaurant, "Description 1", 10.0, "image1.jpg"));
        products.add(new Product("Product 2", ECuisineType.MEXICAN, restaurant, "Description 2", 15.0, "image2.jpg"));
        when(productService.findProductsByRestaurantId(Mockito.anyLong())).thenReturn(products);

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/restaurant/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

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

    @Test
    void testRegisterUser() throws Exception {
        User user = new User("test@example.com", "password123", ERole.CUSTOMER);

        Mockito.when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"email\": \"test@example.com\", \"password\": \"password123\", \"role\": \"CUSTOMER\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testLoginUser() throws Exception {
        User user = new User("test@example.com", "password123", ERole.CUSTOMER);

        Mockito.when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/users/login")
                        .contentType("application/json")
                        .content("{\"email\": \"test@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isOk());
    }

}
