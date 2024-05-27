package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.dtos.RegisterUserDto;
import com.github.tqspolloshermanos.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AdminControllerTest {

    @Test
    void testCreateAdministratorWhenUserAlreadyExists() {
        // Mock UserService
        UserService userService = Mockito.mock(UserService.class);
        // Mock UserService to return null
        when(userService.createAdministrator(any(RegisterUserDto.class))).thenReturn(null);

        // Create instance of AdminController
        AdminController adminController = new AdminController(userService);

        // Call createAdministrator method
        ResponseEntity<String> responseEntity = adminController.createAdministrator(new RegisterUserDto());

        // Assert that response status is BAD_REQUEST and body is "User already exists"
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("User already exists", responseEntity.getBody());
    }
}
