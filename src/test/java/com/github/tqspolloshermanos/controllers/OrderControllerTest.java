package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.dtos.OrderDto;
import com.github.tqspolloshermanos.dtos.PlaceOrderDto;
import com.github.tqspolloshermanos.dtos.OrderItemDto;
import com.github.tqspolloshermanos.entities.*;
import com.github.tqspolloshermanos.services.OrderService;
import com.github.tqspolloshermanos.services.ProductService;
import com.github.tqspolloshermanos.services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    @InjectMocks
    private OrderController orderController;

    private User user;
    private Restaurant restaurant;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("testuser");

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setRestaurant(restaurant);
    }

    @Test
    @WithMockUser
    void testGetUserOrderHistory() {
        Order order = new Order(user, restaurant, LocalDateTime.now(), EOrderStatus.PENDING, Collections.emptyList());
        when(orderService.getUserOrders(any(User.class))).thenReturn(List.of(order));

        ResponseEntity<List<OrderDto>> response = orderController.getUserOrderHistory(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(orderService, times(1)).getUserOrders(user);
    }

    @Test
    @WithMockUser
    void testGetUserOrderHistory_Unauthorized() {
        ResponseEntity<List<OrderDto>> response = orderController.getUserOrderHistory(null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @WithMockUser
    void testPlaceOrder() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setRestaurantId(1L);
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(1L);
        orderItemDto.setQuantity(2);
        placeOrderDto.setItems(Collections.singletonList(orderItemDto));

        when(restaurantService.findRestaurantById(anyLong())).thenReturn(Optional.of(restaurant));
        when(productService.findById(anyLong())).thenReturn(Optional.of(product));
        when(orderService.saveOrder(any(Order.class))).thenReturn(new Order(user, restaurant, LocalDateTime.now(), EOrderStatus.PENDING, Collections.emptyList()));

        ResponseEntity<?> response = orderController.placeOrder(user, placeOrderDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).saveOrder(any(Order.class));
    }

    @Test
    @WithMockUser
    void testPlaceOrder_Unauthorized() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        ResponseEntity<?> response = orderController.placeOrder(null, placeOrderDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    @WithMockUser
    void testPlaceOrder_EmptyItems() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setItems(Collections.emptyList());

        ResponseEntity<?> response = orderController.placeOrder(user, placeOrderDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Order has to have at least one item", response.getBody());
    }

    @Test
    @WithMockUser
    void testPlaceOrder_RestaurantNotFound() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setRestaurantId(1L);
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(1L);
        orderItemDto.setQuantity(2);
        placeOrderDto.setItems(Collections.singletonList(orderItemDto));

        when(restaurantService.findRestaurantById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = orderController.placeOrder(user, placeOrderDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant is not found", response.getBody());
    }

    @Test
    @WithMockUser
    void testPlaceOrder_ProductNotFound() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setRestaurantId(1L);
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(1L);
        orderItemDto.setQuantity(2);
        placeOrderDto.setItems(Collections.singletonList(orderItemDto));

        when(restaurantService.findRestaurantById(anyLong())).thenReturn(Optional.of(restaurant));
        when(productService.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = orderController.placeOrder(user, placeOrderDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody());
    }

    @Test
    @WithMockUser
    void testPlaceOrder_ProductFromDifferentRestaurant() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setRestaurantId(1L);
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(1L);
        orderItemDto.setQuantity(2);
        placeOrderDto.setItems(Collections.singletonList(orderItemDto));

        Restaurant differentRestaurant = new Restaurant();
        differentRestaurant.setId(2L);

        Product differentProduct = new Product();
        differentProduct.setId(2L);
        differentProduct.setRestaurant(differentRestaurant);

        when(restaurantService.findRestaurantById(anyLong())).thenReturn(Optional.of(restaurant));
        when(productService.findById(anyLong())).thenReturn(Optional.of(differentProduct));

        ResponseEntity<?> response = orderController.placeOrder(user, placeOrderDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Products have to be from the same restaurant", response.getBody());
    }

    @Test
    @WithMockUser
    void testPlaceOrder_ItemQuantityZero() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setRestaurantId(1L);
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(1L);
        orderItemDto.setQuantity(0);
        placeOrderDto.setItems(Collections.singletonList(orderItemDto));

        when(restaurantService.findRestaurantById(anyLong())).thenReturn(Optional.of(restaurant));
        when(productService.findById(anyLong())).thenReturn(Optional.of(product));

        ResponseEntity<?> response = orderController.placeOrder(user, placeOrderDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Item quantity cannot be 0", response.getBody());
    }
}
