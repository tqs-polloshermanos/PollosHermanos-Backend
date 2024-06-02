package com.github.tqspolloshermanos.dtos;

import com.github.tqspolloshermanos.entities.OrderItem;
import com.github.tqspolloshermanos.entities.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemDtoTest {

    @Test
    void testOrderItemToOrderItemDtoMapping() {
        // Setup
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("Description 1");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(2);

        // Exercise
        OrderItemDto orderItemDto = new OrderItemDto(orderItem);

        // Verify
        assertEquals(product.getId(), orderItemDto.getProductId());
        assertEquals(product.getName(), orderItemDto.getProductName());
        assertEquals(product.getDescription(), orderItemDto.getProductDescription());
        assertEquals(orderItem.getQuantity(), orderItemDto.getQuantity());
    }

    @Test
    void testOrderItemDtoGettersAndSetters() {
        // Setup
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(1L);
        orderItemDto.setProductName("Product 1");
        orderItemDto.setProductDescription("Description 1");
        orderItemDto.setQuantity(2);

        // Exercise & Verify
        assertEquals(1L, orderItemDto.getProductId());
        assertEquals("Product 1", orderItemDto.getProductName());
        assertEquals("Description 1", orderItemDto.getProductDescription());
        assertEquals(2, orderItemDto.getQuantity());
    }
}
