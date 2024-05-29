package com.github.tqspolloshermanos.repositories;

import com.github.tqspolloshermanos.entities.ECuisineType;
import com.github.tqspolloshermanos.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCuisineType(ECuisineType cuisineType);

}
