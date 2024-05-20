package com.github.tqspolloshermanos.backend.ControllersTests;

import com.github.tqspolloshermanos.backend.Controllers.ProductController;
import com.github.tqspolloshermanos.backend.Entities.Product;
import com.github.tqspolloshermanos.backend.Entities.ProductIngredient;
import com.github.tqspolloshermanos.backend.Services.ProductService;
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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testGetProductIngredients() throws Exception {
        // Mock service response
        Long productId = 1L;
        List<ProductIngredient> ingredients = new ArrayList<>();
        ingredients.add(new ProductIngredient());
        ingredients.add(new ProductIngredient());

        Product product = new Product();
        product.setId(productId);
        product.setProductIngredients(ingredients);

        when(productService.findById(productId)).thenReturn(Optional.of(product));

        // Test controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
