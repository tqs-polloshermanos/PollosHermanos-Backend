package com.github.tqspolloshermanos.repositories;

import com.github.tqspolloshermanos.entities.ECuisineType;
import com.github.tqspolloshermanos.entities.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        Restaurant restaurant1 = new Restaurant("Restaurant A", "Address A", ECuisineType.ITALIAN, "Description A", null, 10);
        Restaurant restaurant2 = new Restaurant("Restaurant B", "Address B", ECuisineType.CHINESE, "Description B", null, 20);
        Restaurant restaurant3 = new Restaurant("Restaurant C", "Address C", ECuisineType.ITALIAN, "Description C", null, 30);

        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);
        restaurantRepository.save(restaurant3);
    }

    @Test
    void testFindByCuisineType() {
        List<Restaurant> italianRestaurants = restaurantRepository.findByCuisineType(ECuisineType.ITALIAN);
        assertNotNull(italianRestaurants);
        assertEquals(2, italianRestaurants.size());
        assertEquals("Restaurant A", italianRestaurants.get(0).getName());
        assertEquals("Restaurant C", italianRestaurants.get(1).getName());
    }
}
