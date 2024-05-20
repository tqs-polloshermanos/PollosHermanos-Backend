package com.github.tqspolloshermanos.backend.ServicesTests;

import com.github.tqspolloshermanos.backend.Entities.Order;
import com.github.tqspolloshermanos.backend.Repositories.OrderRepository;
import com.github.tqspolloshermanos.backend.Services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Test
    void testFindOrdersByUserId() {
        // Mock repository response
        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        OrderService orderService = new OrderService(orderRepository);

        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        when(orderRepository.findByUserId(1L)).thenReturn(orders);

        // Test service method
        List<Order> result = orderService.findOrdersByUserId(1L);
        assertEquals(2, result.size());
    }

    @Test
    void testPlaceOrder() {
        // Mock repository response
        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        OrderService orderService = new OrderService(orderRepository);

        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        // Test service method
        Order placedOrder = orderService.placeOrder(order);
        assertEquals(order, placedOrder);
    }

    @Test
    void testFindOrderById() {
        // Mock repository response
        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        OrderService orderService = new OrderService(orderRepository);

        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Test service method
        Order result = orderService.findOrderById(1L);
        assertEquals(order, result);
    }
}
