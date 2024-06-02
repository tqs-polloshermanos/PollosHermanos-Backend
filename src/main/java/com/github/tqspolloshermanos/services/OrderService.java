package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.entities.EOrderStatus;
import com.github.tqspolloshermanos.entities.Order;
import com.github.tqspolloshermanos.entities.User;
import com.github.tqspolloshermanos.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getUserOrders(User user) {
        Long userId = user.getId();
        return orderRepository.findByUserId(userId);
    }
    public boolean userHasPendingOrders(User user) {
        return orderRepository.existsByUserAndStatus(user, EOrderStatus.PENDING);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // Actions only for employees

    public List<Order> getRestaurantOrders(Long restaurantId, Set<EOrderStatus> statuses) {
        if (statuses.isEmpty()) {
            return orderRepository.findAllByRestaurantId(restaurantId);
        }

        List<Order> orders = new java.util.ArrayList<>(Collections.emptyList());
        for (EOrderStatus status : statuses) {
            orders.addAll(orderRepository.findAllByRestaurantIdAndStatus(restaurantId, status));
        }
        return orders;
    }

    public void updateOrderStatus(Order order, EOrderStatus status) {
        order.setStatus(status);
        orderRepository.save(order);
    }
}
