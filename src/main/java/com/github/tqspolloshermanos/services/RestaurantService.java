package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.entities.ECuisineType;
import com.github.tqspolloshermanos.entities.Restaurant;
import com.github.tqspolloshermanos.repositories.RestaurantRepository;
import com.github.tqspolloshermanos.entities.Restaurant;
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

    public List<Restaurant> filterRestaurants(ECuisineType cuisineType, String name) {
        return restaurantRepository.findByCuisineType(cuisineType);
    }
}
