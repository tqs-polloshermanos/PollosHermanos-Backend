package com.github.tqspolloshermanos.backend.Services;

import com.github.tqspolloshermanos.backend.Entities.ProductIngredients;
import com.github.tqspolloshermanos.backend.Repositories.ProductIngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductIngredientsService {

    @Autowired
    private ProductIngredientsRepository productIngredientsRepository;

    public List<ProductIngredients> getAllProductIngredients() {
        return productIngredientsRepository.findAll();
    }

    public Optional<ProductIngredients> getProductIngredientsById(Long productIngredientsId) {
        return productIngredientsRepository.findById(productIngredientsId);
    }

    public ProductIngredients saveProductIngredients(ProductIngredients productIngredients) {
        return productIngredientsRepository.save(productIngredients);
    }

    public void deleteProductIngredientsById(Long productIngredientsId) {
        productIngredientsRepository.deleteById(productIngredientsId);
    }
}
