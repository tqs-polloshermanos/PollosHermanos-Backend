package com.github.tqspolloshermanos.backend.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Restaurants")
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(nullable = false, name = "restaurant_name")
    private String restaurantName;

    @Column(nullable = false, name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "cuisine_type")
    private CuisineType cuisineType;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, name = "restaurant_image_path")
    private String restaurantImagePath;

    @Column(name = "number_of_orders")
    private int numberOfOrders; 

    public Restaurant(String restaurantName, String address, CuisineType cuisineType, String description, String restaurantImagePath) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.cuisineType = cuisineType;
        this.description = description;
        this.restaurantImagePath = restaurantImagePath;
        this.numberOfOrders = 0;
    }

    public Restaurant(String restaurantName, String address, CuisineType cuisineType, String restaurantImagePath) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.cuisineType = cuisineType;
        this.restaurantImagePath = restaurantImagePath;
        this.numberOfOrders = 0;
    }

    public Restaurant() {
        this.numberOfOrders = 0;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestaurantImagePath() {
        return restaurantImagePath;
    }

    public void setRestaurantImagePath(String restaurantImagePath) {
        this.restaurantImagePath = restaurantImagePath;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public void incrementNumberOfOrders() {
        if (this.numberOfOrders >= 9999) {
            this.numberOfOrders = 0;
        }
        else {
            this.numberOfOrders++;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((restaurantId == null) ? 0 : restaurantId.hashCode());
        result = prime * result + ((restaurantName == null) ? 0 : restaurantName.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((cuisineType == null) ? 0 : cuisineType.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((restaurantImagePath == null) ? 0 : restaurantImagePath.hashCode());
        result = prime * result + numberOfOrders;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant that)) return false;
        return getNumberOfOrders() == that.getNumberOfOrders() &&
                Objects.equals(getRestaurantId(), that.getRestaurantId()) &&
                Objects.equals(getRestaurantName(), that.getRestaurantName()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                getCuisineType() == that.getCuisineType() &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getRestaurantImagePath(), that.getRestaurantImagePath());
    }

    @Override
    public String toString() {
        return "Restaurant [restaurantId=" + restaurantId + ", restaurantName=" + restaurantName + ", address="
                + address + ", cuisineType=" + cuisineType + ", description=" + description + ", restaurantImagePath="
                + restaurantImagePath + ", numberOfOrders=" + numberOfOrders + "]";
    }
}
