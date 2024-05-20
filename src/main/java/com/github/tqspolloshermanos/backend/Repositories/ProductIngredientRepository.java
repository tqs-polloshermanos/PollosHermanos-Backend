package com.github.tqspolloshermanos.backend.Repositories;

import com.github.tqspolloshermanos.backend.Entities.Product;
import com.github.tqspolloshermanos.backend.Entities.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long> {
    List<ProductIngredient> findByProduct(Product product);
}
