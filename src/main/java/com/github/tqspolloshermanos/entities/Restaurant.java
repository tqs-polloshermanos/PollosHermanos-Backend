package com.github.tqspolloshermanos.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "cuisine_type")
    private ECuisineType cuisineType;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "number_of_orders")
    private int numberOfOrders;

    // Constructors
    public Restaurant() {
    }

    public Restaurant(String name, String address, ECuisineType cuisineType, String description, byte[] image, int numberOfOrders) {
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.description = description;
        this.image = image;
        this.numberOfOrders = numberOfOrders;
    }

    // Getters and Setters
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ECuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(ECuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

