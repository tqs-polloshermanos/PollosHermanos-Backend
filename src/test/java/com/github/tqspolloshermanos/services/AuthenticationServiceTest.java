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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class AuthenticationServiceTest {

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
    void testAuthenticate_Success() {
        // Given
        LoginUserDto input = new LoginUserDto().setEmail("user@example.com").setPassword("password");
        User user = new User().setId(1L).setEmail("user@example.com").setPassword("encodedPassword");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        // When
        User authenticatedUser = authenticationService.authenticate(input);

        // Then
        assertThat(authenticatedUser).isNotNull();
        assertThat(authenticatedUser.getEmail()).isEqualTo(input.getEmail());
        verify(userRepository, times(1)).findByEmail("user@example.com");
    }

    @Test
    void testAuthenticate_UserNotFound() {
        // Given
        LoginUserDto input = new LoginUserDto().setEmail("nonexistent@example.com").setPassword("password");
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When/Then
        assertThrows(Exception.class, () -> authenticationService.authenticate(input));
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void testSignup_Success() {
        // Given
        RegisterUserDto input = new RegisterUserDto()
                .setFullName("John Doe")
                .setEmail("john.doe@example.com")
                .setPassword("password");

        Role userRole = new Role();
        userRole.setId(1);
        userRole.setName(RoleEnum.USER);
        User newUser = new User()
                .setId(1L)
                .setFullName("John Doe")
                .setEmail("john.doe@example.com")
                .setPassword("encodedPassword")
                .setRole(userRole);

        when(userRepository.findByEmail(input.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName(RoleEnum.USER)).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(input.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // When
        User createdUser = authenticationService.signup(input);

        // Then
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFullName()).isEqualTo(input.getFullName());
        assertThat(createdUser.getEmail()).isEqualTo(input.getEmail());
        assertThat(createdUser.getPassword()).isEqualTo("encodedPassword");
        assertThat(createdUser.getRole()).isEqualTo(userRole);

        verify(userRepository, times(1)).findByEmail(input.getEmail());
        verify(roleRepository, times(1)).findByName(RoleEnum.USER);
        verify(passwordEncoder, times(1)).encode(input.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
