package com.github.tqspolloshermanos.dtos;

import java.util.List;

public class PlaceOrderDto {

    Long restaurantId;

    List<OrderItemDto> items;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
