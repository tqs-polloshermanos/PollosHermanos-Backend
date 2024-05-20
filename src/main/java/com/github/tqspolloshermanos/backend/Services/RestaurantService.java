package com.github.tqspolloshermanos.backend.Services;

import com.github.tqspolloshermanos.backend.Entities.ECuisineType;
import com.github.tqspolloshermanos.backend.Entities.Restaurant;
import com.github.tqspolloshermanos.backend.Repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> findRestaurantsByCuisineType(ECuisineType cuisineType) {
        return restaurantRepository.findByCuisineType(cuisineType);
    }
}
