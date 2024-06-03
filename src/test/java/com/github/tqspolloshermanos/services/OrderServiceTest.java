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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Test
    void testGetRestaurantOrders_EmptyStatuses() {
        Long restaurantId = 1L;
        List<Order> orders = List.of(new Order(), new Order());
        when(orderRepository.findAllByRestaurantId(restaurantId)).thenReturn(orders);

        List<Order> result = orderService.getRestaurantOrders(restaurantId, Collections.emptySet());

        assertEquals(orders.size(), result.size());
        verify(orderRepository, times(1)).findAllByRestaurantId(restaurantId);
    }

    @Test
    void testGetRestaurantOrders_WithStatuses() {
        Long restaurantId = 1L;
        Set<EOrderStatus> statuses = Set.of(EOrderStatus.PENDING, EOrderStatus.PROCESSING);
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> pendingOrders = List.of(order1);
        List<Order> paidOrders = List.of(order2);

        when(orderRepository.findAllByRestaurantIdAndStatus(restaurantId, EOrderStatus.PENDING)).thenReturn(pendingOrders);
        when(orderRepository.findAllByRestaurantIdAndStatus(restaurantId, EOrderStatus.PROCESSING)).thenReturn(paidOrders);

        List<Order> result = orderService.getRestaurantOrders(restaurantId, statuses);

        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAllByRestaurantIdAndStatus(restaurantId, EOrderStatus.PENDING);
        verify(orderRepository, times(1)).findAllByRestaurantIdAndStatus(restaurantId, EOrderStatus.PROCESSING);
    }

    @Test
    void testUpdateOrderStatus() {
        Order order = new Order();
        EOrderStatus newStatus = EOrderStatus.PROCESSING;

        orderService.updateOrderStatus(order, newStatus);

        assertEquals(newStatus, order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testIsOrderPaidFor() {
        Order order = new Order();
        order.setStatus(EOrderStatus.PENDING);

        boolean result = orderService.isOrderPaidFor(order);

        assertFalse(result, "Order with PENDING status should not be considered paid for.");
    }

    @Test
    void testIsUserOwner() {
        User user = new User();
        user.setId(1L);

        Order order = new Order();
        User orderUser = new User();
        orderUser.setId(1L);
        order.setUser(orderUser);

        boolean result = orderService.isUserOwner(user, order);

        assertTrue(result, "User should be considered the owner of the order.");
    }

    @Test
    void testGetProcessingOrdersByRestaurant() {
        Long restaurantId = 1L;
        List<Order> expectedOrders = new ArrayList<>();
        when(orderRepository.findByRestaurantIdAndStatusOrderByOrderNumberAsc(restaurantId, EOrderStatus.PROCESSING)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getProcessingOrdersByRestaurant(restaurantId);

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void testGetDoneOrdersByRestaurant() {
        Long restaurantId = 1L;
        List<Order> expectedOrders = new ArrayList<>();
        when(orderRepository.findByRestaurantIdAndStatusOrderByOrderNumberAsc(restaurantId, EOrderStatus.DONE)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getDoneOrdersByRestaurant(restaurantId);

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void testGetLastOrderForRestaurant() {
        Long restaurantId = 1L;
        Optional<Order> expectedOrder = Optional.empty();
        when(orderRepository.findTopByRestaurantIdOrderByOrderNumberDesc(restaurantId)).thenReturn(expectedOrder);

        Optional<Order> actualOrder = orderService.getLastOrderForRestaurant(restaurantId);

        assertEquals(expectedOrder, actualOrder);
    }
}
