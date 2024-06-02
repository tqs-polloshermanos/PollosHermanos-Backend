package com.github.tqspolloshermanos.dtos;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlaceOrderDtoTest {

    @Test
    void testGettersAndSetters() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();

        // Test default values
        assertNull(placeOrderDto.getRestaurantId());
        assertNull(placeOrderDto.getItems());

        // Test setting and getting restaurantId
        Long restaurantId = 1L;
        placeOrderDto.setRestaurantId(restaurantId);
        assertEquals(restaurantId, placeOrderDto.getRestaurantId());

        // Test setting and getting items
        OrderItemDto orderItemDto1 = new OrderItemDto();
        orderItemDto1.setProductId(1L);
        orderItemDto1.setQuantity(2);

        OrderItemDto orderItemDto2 = new OrderItemDto();
        orderItemDto2.setProductId(2L);
        orderItemDto2.setQuantity(3);

        List<OrderItemDto> items = Arrays.asList(orderItemDto1, orderItemDto2);
        placeOrderDto.setItems(items);
        assertEquals(items, placeOrderDto.getItems());
    }

    @Test
    void testEmptyItemsList() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();

        // Test setting an empty items list
        List<OrderItemDto> emptyItems = Arrays.asList();
        placeOrderDto.setItems(emptyItems);
        assertEquals(emptyItems, placeOrderDto.getItems());
    }
}
