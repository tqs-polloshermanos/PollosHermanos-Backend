package com.github.tqspolloshermanos.backend.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "cuisine_type")
    private ECuisineType cuisineType;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "price")
    private double price;

    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(mappedBy = "product")
    private List<ProductIngredient> productIngredients;

    // Constructors
    public Product() {
    }

    public Product(String name, ECuisineType cuisineType, Restaurant restaurant, String description, double price, String imagePath) {
        this.name = name;
        this.cuisineType = cuisineType;
        this.restaurant = restaurant;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
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

    public ECuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(ECuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<ProductIngredient> getProductIngredients() {
        return productIngredients;
    }

    public void setProductIngredients(List<ProductIngredient> productIngredients) {
        this.productIngredients = productIngredients;
    }
}
