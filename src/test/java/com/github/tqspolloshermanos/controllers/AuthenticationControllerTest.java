package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.controllers.AuthenticationController;
import com.github.tqspolloshermanos.dtos.LoginUserDto;
import com.github.tqspolloshermanos.dtos.RegisterUserDto;
import com.github.tqspolloshermanos.entities.User;
import com.github.tqspolloshermanos.responses.LoginResponse;
import com.github.tqspolloshermanos.services.AuthenticationService;
import com.github.tqspolloshermanos.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegister() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        User registeredUser = new User(); // create a mock user

        when(authenticationService.signup(registerUserDto)).thenReturn(registeredUser);

        ResponseEntity<String> responseEntity = authenticationController.register(registerUserDto);

        verify(authenticationService, times(1)).signup(registerUserDto);
        verifyNoMoreInteractions(authenticationService);

        // Add assertions based on expected behavior
        assertEquals("User registered successfully", responseEntity.getBody());
    }

    @Test
    void testAuthenticate() {
        LoginUserDto loginUserDto = new LoginUserDto();
        User authenticatedUser = new User(); // create a mock user
        String jwtToken = "mock_jwt_token";
        long expiresIn = 3600000;

        when(authenticationService.authenticate(loginUserDto)).thenReturn(authenticatedUser);
        when(jwtService.generateToken(authenticatedUser)).thenReturn(jwtToken);
        when(jwtService.getExpirationTime()).thenReturn(expiresIn); // Stub the expiration time

        ResponseEntity<LoginResponse> responseEntity = authenticationController.authenticate(loginUserDto);

        verify(authenticationService, times(1)).authenticate(loginUserDto);
        verify(jwtService, times(1)).generateToken(authenticatedUser);

        // Verify the token and expiration time separately
        assertEquals(jwtToken, responseEntity.getBody().getToken());
        assertEquals(expiresIn, responseEntity.getBody().getExpiresIn());
    }
}
