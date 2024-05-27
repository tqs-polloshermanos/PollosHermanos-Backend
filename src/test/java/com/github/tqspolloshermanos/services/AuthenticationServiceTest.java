package com.github.tqspolloshermanos.services;

import com.github.tqspolloshermanos.dtos.LoginUserDto;
import com.github.tqspolloshermanos.dtos.RegisterUserDto;
import com.github.tqspolloshermanos.entities.Role;
import com.github.tqspolloshermanos.entities.RoleEnum;
import com.github.tqspolloshermanos.entities.User;
import com.github.tqspolloshermanos.repositories.RoleRepository;
import com.github.tqspolloshermanos.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testAuthenticate_Success() {
        // Given
        LoginUserDto input = new LoginUserDto().setEmail("user@example.com").setPassword("password");
        User user = new User().setId(1).setEmail("user@example.com").setPassword("encodedPassword");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        // When
        User authenticatedUser = authenticationService.authenticate(input);

        // Then
        assertThat(authenticatedUser).isNotNull();
        assertThat(authenticatedUser.getEmail()).isEqualTo(input.getEmail());
        verify(userRepository, times(1)).findByEmail("user@example.com");
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        // Given
        LoginUserDto input = new LoginUserDto().setEmail("nonexistent@example.com").setPassword("password");
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When/Then
        assertThrows(Exception.class, () -> authenticationService.authenticate(input));
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }
}
