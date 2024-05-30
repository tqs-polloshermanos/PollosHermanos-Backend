package com.github.tqspolloshermanos.repositories;

import com.github.tqspolloshermanos.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByRestaurantId(Long restaurantId);

    Optional<Product> findByName(String name);
}
