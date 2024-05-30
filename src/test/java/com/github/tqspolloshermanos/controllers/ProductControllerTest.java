package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.dtos.ProductDto;
import com.github.tqspolloshermanos.entities.Product;
import com.github.tqspolloshermanos.entities.Restaurant;
import com.github.tqspolloshermanos.entities.ECuisineType;
import com.github.tqspolloshermanos.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductService productService;
    private ProductController productController;

    private Product product;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setCuisineType(ECuisineType.ITALIAN);
        restaurant.setDescription("A test restaurant");
        restaurant.setNumberOfOrders(0);

        product = new Product();
        product.setId(1L);
        product.setName("Pizza Royal");
        product.setRestaurant(restaurant);
        product.setDescription("Delicious pizza with pepperoni");
        product.setPrice(1000);
    }

    @Test
    void testGetAllProducts() {
        when(productService.findAll()).thenReturn(List.of(product));

        List<ProductDto> productDtos = productController.getAllProducts();

        assertEquals(1, productDtos.size());
        assertEquals("Pizza Royal", productDtos.get(0).getName());
        verify(productService, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        when(productService.findById(1L)).thenReturn(Optional.of(product));

        ResponseEntity<ProductDto> responseEntity = productController.getProductById(1L);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Pizza Royal", responseEntity.getBody().getName());
        verify(productService, times(1)).findById(1L);
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ProductDto> responseEntity = productController.getProductById(1L);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() == null);
        verify(productService, times(1)).findById(1L);
    }

    @Test
    void testGetProductsByRestaurantId() {
        when(productService.findProductsByRestaurantId(1L)).thenReturn(List.of(product));

        ResponseEntity<List<ProductDto>> responseEntity = productController.getProductsByRestaurantId(1L);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("Pizza Royal", responseEntity.getBody().get(0).getName());
        verify(productService, times(1)).findProductsByRestaurantId(1L);
    }

    @Test
    void testGetProductsByRestaurantIdNotFound() {
        when(productService.findProductsByRestaurantId(1L)).thenReturn(List.of());

        ResponseEntity<List<ProductDto>> responseEntity = productController.getProductsByRestaurantId(1L);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() == null);
        verify(productService, times(1)).findProductsByRestaurantId(1L);
    }
}
