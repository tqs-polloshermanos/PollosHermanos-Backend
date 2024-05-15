package com.github.tqspolloshermanos.backend.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false, name = "product_name")
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "cuisine_type")
    private CuisineType cuisineType;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "price")
    private double price;

    @Column(name = "product_image_path")
    private String productImagePath;

    public Product(String productName, CuisineType cuisineType, Restaurant restaurant, String description, double price, String productImagePath) {
        this.productName = productName;
        this.cuisineType = cuisineType;
        this.restaurant = restaurant;
        this.description = description;
        this.price = price;
        this.productImagePath = productImagePath;
    }

    public Product(String productName, CuisineType cuisineType, Restaurant restaurant, String description, double price) {
        this.productName = productName;
        this.cuisineType = cuisineType;
        this.restaurant = restaurant;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
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

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        result = prime * result + ((productName == null) ? 0 : productName.hashCode());
        result = prime * result + ((cuisineType == null) ? 0 : cuisineType.hashCode());
        result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((productImagePath == null) ? 0 : productImagePath.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Double.compare(getPrice(), product.getPrice()) == 0 &&
                Objects.equals(getProductId(), product.getProductId()) &&
                Objects.equals(getProductName(), product.getProductName()) &&
                getCuisineType() == product.getCuisineType() &&
                Objects.equals(getRestaurant(), product.getRestaurant()) &&
                Objects.equals(getDescription(), product.getDescription()) &&
                Objects.equals(getProductImagePath(), product.getProductImagePath());
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", cuisineType=" + cuisineType
                + ", restaurant=" + restaurant + ", description=" + description + ", price=" + price
                + ", productImagePath=" + productImagePath + "]";
    }

}
