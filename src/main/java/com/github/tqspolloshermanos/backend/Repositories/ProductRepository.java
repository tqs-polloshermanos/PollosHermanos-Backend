package com.github.tqspolloshermanos.backend.Repositories;

import com.github.tqspolloshermanos.backend.Entities.ECuisineType;
import com.github.tqspolloshermanos.backend.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByRestaurantId(Long restaurantId);

    List<Product> findByCuisineType(ECuisineType cuisineType);
}
