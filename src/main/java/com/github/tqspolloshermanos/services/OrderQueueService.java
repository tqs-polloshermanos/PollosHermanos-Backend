package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.entities.Order;
import com.github.tqspolloshermanos.entities.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderQueueService {

    private final OrderService orderService;
    private final RestaurantService restaurantService;
    private static final int MAX_ORDER_NUMBER = 999;

    public OrderQueueService(OrderService orderService, RestaurantService restaurantService) {
        this.orderService = orderService;
        this.restaurantService = restaurantService;
    }

    @Transactional
    public synchronized Integer generateOrderNumber(Restaurant restaurant) {
        // Retrieve the last order number for the restaurant and increment it
        Optional<Order> lastOrderOpt = orderService.getLastOrderForRestaurant(restaurant.getId());
        Integer lastOrderNumber = lastOrderOpt.map(Order::getOrderNumber).orElse(0);

        // Reset the order number if it exceeds the maximum value
        if (lastOrderNumber >= MAX_ORDER_NUMBER) {
            return 1;
        } else {
            return lastOrderNumber + 1;
        }
    }
}
