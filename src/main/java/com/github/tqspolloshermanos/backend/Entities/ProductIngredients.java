package com.github.tqspolloshermanos.backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductIngredients")
public class ProductIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_ingredients_id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    private Product product;

    @Column(nullable = false, name = "quantity")
    private int quantity;

    // Constructors
    public ProductIngredients() {
    }

    public ProductIngredients(Ingredient ingredient, Product product, int quantity) {
        this.ingredient = ingredient;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getProductIngredientsId() {
        return id;
    }

    public void setProductIngredientsId(Long productIngredientsId) {
        this.id = productIngredientsId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
