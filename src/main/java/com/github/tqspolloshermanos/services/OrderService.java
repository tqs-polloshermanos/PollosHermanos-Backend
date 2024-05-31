package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.entities.Order;
import com.github.tqspolloshermanos.entities.OrderItem;
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

    public Integer getTotalPrice(Order order) {
        Integer total = 0;
        List<OrderItem> items = order.getOrderItems();
        for (OrderItem item : items) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }

    public List<Order> getUserOrders(User user) {
        Long userId = Long.valueOf(user.getId());
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders;
    }

    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
