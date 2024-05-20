package com.github.tqspolloshermanos.backend.ControllersTests;

import com.github.tqspolloshermanos.backend.Controllers.IngredientController;
import com.github.tqspolloshermanos.backend.Entities.Ingredient;
import com.github.tqspolloshermanos.backend.Services.IngredientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Test
    void testGetAllIngredients() throws Exception {
        // Mock service response
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Salt", "Common table salt"));
        ingredients.add(new Ingredient("Sugar", "Granulated sugar"));

        when(ingredientService.findAll()).thenReturn(ingredients);

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetIngredientById() throws Exception {
        // Mock service response
        Ingredient ingredient = new Ingredient("Salt", "Common table salt");
        Long ingredientId = 1L;
        when(ingredientService.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ingredients/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Add more test methods as needed
}
