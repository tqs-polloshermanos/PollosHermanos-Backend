package com.github.tqspolloshermanos.backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

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

    // Constructors
    public Restaurant() {
    }

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

    // Getters and Setters
    public Long getRestaurantId() {
        return id;
    }

    public void setRestaurantId(Long restaurantId) {
        this.id = restaurantId;
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
        } else {
            this.numberOfOrders++;
        }
    }
}
