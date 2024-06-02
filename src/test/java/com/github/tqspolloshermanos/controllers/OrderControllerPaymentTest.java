package com.github.tqspolloshermanos.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;

import com.github.tqspolloshermanos.entities.EOrderStatus;
import com.github.tqspolloshermanos.entities.Order;
import com.github.tqspolloshermanos.entities.User;
import com.github.tqspolloshermanos.services.OrderService;
import com.github.tqspolloshermanos.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class OrderControllerPaymentTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderController orderController;

    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        order = new Order();
        order.setId(1L);
        order.setUser(user);
    }

    @Test
    void testPayForOrder_Unauthorized() {
        ResponseEntity<String> response = orderController.payForOrder(null, 1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testPayForOrder_OrderNotFound() {
        when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = orderController.payForOrder(user, 1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order not found", response.getBody());
    }

    @Test
    void testPayForOrder_OrderAlreadyPaidFor() {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        when(orderService.isOrderPaidFor(order)).thenReturn(true);

        ResponseEntity<String> response = orderController.payForOrder(user, 1L);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Order has been already paid for", response.getBody());
    }

    @Test
    void testPayForOrder_Success() {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        when(orderService.isOrderPaidFor(order)).thenReturn(false);
        when(userService.isUserEmployee(user)).thenReturn(false);
        when(orderService.isUserOwner(user, order)).thenReturn(true);

        ResponseEntity<String> response = orderController.payForOrder(user, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order paid for successfully", response.getBody());

        verify(orderService, times(1)).updateOrderStatus(order, EOrderStatus.PROCESSING);
    }
}
