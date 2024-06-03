package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.entities.Order;
import com.github.tqspolloshermanos.entities.Restaurant;
import com.github.tqspolloshermanos.services.OrderService;
import com.github.tqspolloshermanos.services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerQueueTest {

    @Mock
    private OrderService orderService;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testGetOrderQueue() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        when(restaurantService.findRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));

        List<Order> orders = new ArrayList<>();
        when(orderService.getProcessingOrdersByRestaurant(restaurantId)).thenReturn(orders);

        ResponseEntity<Map<String, Object>> responseEntity = orderController.getOrderQueue(restaurantId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().get("count"));
        assertEquals(orders, responseEntity.getBody().get("orders"));
    }

    @Test
    void testGetDoneOrders() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        when(restaurantService.findRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));

        List<Order> orders = new ArrayList<>();
        when(orderService.getDoneOrdersByRestaurant(restaurantId)).thenReturn(orders);

        ResponseEntity<Map<String, Object>> responseEntity = orderController.getDoneOrders(restaurantId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().get("count"));
        assertEquals(orders, responseEntity.getBody().get("orders"));
    }

}
