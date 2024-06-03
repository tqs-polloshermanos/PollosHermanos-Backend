package com.github.tqspolloshermanos.dtos;

import com.github.tqspolloshermanos.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String restaurantName;
    private ECuisineType cuisineType;
    private LocalDateTime orderDate;
    private EOrderStatus status;
    private int orderNumber;
    private final List<OrderItemDto> orderItems = new ArrayList<>();

    public OrderDto() {

    }

    public OrderDto(Order order) {
        id = order.getId();
        userId = order.getUser().getId();
        orderDate = order.getOrderDate();
        status = order.getStatus();

        Restaurant restaurant = order.getRestaurant();
        restaurantId = restaurant.getId();
        restaurantName = restaurant.getName();
        cuisineType = restaurant.getCuisineType();
        orderNumber = order.getOrderNumber();

        order.getOrderItems().forEach((item) -> orderItems.add(new OrderItemDto(item)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public ECuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(ECuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public EOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EOrderStatus status) {
        this.status = status;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
