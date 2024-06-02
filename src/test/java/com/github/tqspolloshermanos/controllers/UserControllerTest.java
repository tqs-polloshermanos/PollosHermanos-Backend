package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.entities.User;
import com.github.tqspolloshermanos.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Test
    void testAuthenticatedUser() {
        // Mock UserService
        UserService userService = mock(UserService.class);

        // Create a test user
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        // Mock Authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        // Set the authentication in SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create instance of UserController
        UserController userController = new UserController(userService);

        // Call authenticatedUser method
        ResponseEntity<User> responseEntity = userController.authenticatedUser();

        // Assert that response status is OK and body is the same as the authenticated user
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }
}
