package com.github.tqspolloshermanos.backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(nullable = false, name = "ingredient_name")
    private String ingredientName;

    @Column(name = "description")
    private String description;

    // Constructors
    public Ingredient() {
    }

    public Ingredient(String ingredientName, String description) {
        this.ingredientName = ingredientName;
        this.description = description;
    }

    // Getters and Setters
    public Long getIngredientId() {
        return id;
    }

    public void setIngredientId(Long ingredientId) {
        this.id = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
