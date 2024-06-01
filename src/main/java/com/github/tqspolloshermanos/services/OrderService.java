package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.entities.EOrderStatus;
import com.github.tqspolloshermanos.entities.Order;
import com.github.tqspolloshermanos.entities.User;
import com.github.tqspolloshermanos.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
