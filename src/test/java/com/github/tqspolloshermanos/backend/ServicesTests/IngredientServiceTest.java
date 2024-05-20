package com.github.tqspolloshermanos.backend.ServicesTests;

import com.github.tqspolloshermanos.backend.Entities.Ingredient;
import com.github.tqspolloshermanos.backend.Repositories.IngredientRepository;
import com.github.tqspolloshermanos.backend.Services.IngredientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class IngredientServiceTest {

    @Test
    void testFindAll() {
        // Mock repository response
        IngredientRepository ingredientRepository = Mockito.mock(IngredientRepository.class);
        IngredientService ingredientService = new IngredientService(ingredientRepository);

        List<Ingredient> expectedIngredients = new ArrayList<>();
        expectedIngredients.add(new Ingredient("Salt", "Common table salt"));
        expectedIngredients.add(new Ingredient("Sugar", "Granulated sugar"));

        when(ingredientRepository.findAll()).thenReturn(expectedIngredients);

        // Test service method
        List<Ingredient> foundIngredients = ingredientService.findAll();
        assertEquals(expectedIngredients, foundIngredients);
    }

    @Test
    void testFindById() {
        // Mock repository response
        IngredientRepository ingredientRepository = Mockito.mock(IngredientRepository.class);
        IngredientService ingredientService = new IngredientService(ingredientRepository);

        Ingredient expectedIngredient = new Ingredient("Salt", "Common table salt");
        Long ingredientId = 1L;

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(expectedIngredient));

        // Test service method
        Optional<Ingredient> foundIngredient = ingredientService.findById(ingredientId);
        assertEquals(Optional.of(expectedIngredient), foundIngredient);
    }

    // Add more test methods as needed
}
