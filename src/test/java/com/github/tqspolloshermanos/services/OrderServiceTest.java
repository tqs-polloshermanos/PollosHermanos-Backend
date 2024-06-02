package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.entities.EOrderStatus;
import com.github.tqspolloshermanos.entities.Order;
import com.github.tqspolloshermanos.entities.User;
import com.github.tqspolloshermanos.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testGetUserOrders() {
        User user = new User();
        user.setId(1L);

        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        List<Order> orders = Arrays.asList(order1, order2);
        when(orderRepository.findByUserId(user.getId())).thenReturn(orders);

        List<Order> result = orderService.getUserOrders(user);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void testUserHasPendingOrders() {
        User user = new User();
        user.setId(1L);

        when(orderRepository.existsByUserAndStatus(eq(user), eq(EOrderStatus.PENDING))).thenReturn(true);

        boolean result = orderService.userHasPendingOrders(user);

        assertTrue(result);
    }

    @Test
    void testSaveOrder() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.saveOrder(order);

        assertEquals(1L, result.getId());
    }
}
