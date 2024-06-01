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
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
