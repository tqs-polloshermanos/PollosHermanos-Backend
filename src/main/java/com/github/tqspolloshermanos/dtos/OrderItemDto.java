package com.github.tqspolloshermanos.dtos;

import com.github.tqspolloshermanos.entities.OrderItem;
import com.github.tqspolloshermanos.entities.Product;

public class OrderItemDto {
    Long productId;

    String productName;
    String productDescription;
    Integer quantity;

    public OrderItemDto() {

    }

    public OrderItemDto(OrderItem item) {
        Product product = item.getProduct();

        productId = product.getId();
        productName = product.getName();
        productDescription = product.getDescription();
        quantity = item.getQuantity();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
