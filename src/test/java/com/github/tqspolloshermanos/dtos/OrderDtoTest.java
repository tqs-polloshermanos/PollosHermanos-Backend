package com.github.tqspolloshermanos.dtos;

import com.github.tqspolloshermanos.dtos.OrderDto;
import com.github.tqspolloshermanos.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDtoTest {

    @Test
    public void testOrderToOrderDtoMapping() {
        // Setup
        User user = new User();
        user.setId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Restaurant 1");
        restaurant.setCuisineType(ECuisineType.ITALIAN);

        Product product = new Product();
        product.setId(1L);
        product.setName("Pizza");
        product.setDescription("Tasty pizza");
        product.setPrice(100);
        product.setRestaurant(restaurant);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProduct(product);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(EOrderStatus.PENDING);
        order.setRestaurant(restaurant);
        order.setOrderItems(Arrays.asList(orderItem));

        // Exercise
        OrderDto orderDto = new OrderDto(order);

        // Verify
        assertEquals(order.getId(), orderDto.getId());
        assertEquals(user.getId(), orderDto.getUserId());
        assertEquals(restaurant.getId(), orderDto.getRestaurantId());
        assertEquals(restaurant.getName(), orderDto.getRestaurantName());
        assertEquals(restaurant.getCuisineType(), orderDto.getCuisineType());
        assertEquals(order.getOrderDate(), orderDto.getOrderDate());
        assertEquals(order.getStatus(), orderDto.getStatus());
        assertEquals(1, orderDto.getOrderItems().size());
    }
}
