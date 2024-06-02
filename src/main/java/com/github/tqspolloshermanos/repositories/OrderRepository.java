package com.github.tqspolloshermanos.repositories;

import com.github.tqspolloshermanos.entities.EOrderStatus;
import com.github.tqspolloshermanos.entities.Order;
import com.github.tqspolloshermanos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    boolean existsByUserAndStatus(User user, EOrderStatus status);

    List<Order> findAllByRestaurantId(Long restaurantId);

    List<Order> findAllByRestaurantIdAndStatus(Long restaurantId, EOrderStatus status);
}
