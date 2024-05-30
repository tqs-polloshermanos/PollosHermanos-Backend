package com.github.tqspolloshermanos.dtos;

import com.github.tqspolloshermanos.entities.ECuisineType;

public class ProductDto {
    private Long id;

    private String name;
    private Long restaurantId;
    private String restaurantName;

    private ECuisineType cuisineType;

    private String description;

    private Integer price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    public ECuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(ECuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }
}
