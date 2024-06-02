package com.github.tqspolloshermanos.services;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    void testAllUsers() {
        // Given
        List<User> userList = new ArrayList<>();
        userList.add(new User().setId(1L).setFullName("User 1").setEmail("user1@example.com"));
        userList.add(new User().setId(2L).setFullName("User 2").setEmail("user2@example.com"));

        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<User> allUsers = userService.allUsers();

        // Then
        assertThat(allUsers).isNotNull();
        assertThat(allUsers.size()).isEqualTo(userList.size());
        for (int i = 0; i < userList.size(); i++) {
            assertThat(allUsers.get(i)).isEqualTo(userList.get(i));
        }
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreateAdministrator_Success() {
        // Given
        RegisterUserDto input = new RegisterUserDto();
        input.setEmail("admin@example.com");
        input.setPassword("password");
        input.setFullName("Admin");
        
        Role adminRole = new Role().setName(RoleEnum.ADMIN);
        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));
        when(userRepository.save(any(User.class))).thenReturn(new User().setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword("encodedPassword") // Assuming password encoding will be done by the service
                .setRole(adminRole));

        // When
        User createdUser = userService.createAdministrator(input);

        // Then
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo(input.getEmail());
        assertThat(createdUser.getFullName()).isEqualTo(input.getFullName());
        assertThat(createdUser.getRole()).isEqualTo(adminRole);
        assertThat(createdUser.getUsername()).isEqualTo("admin@example.com");
        assertThat(createdUser.isAccountNonExpired()).isTrue();
        assertThat(createdUser.isAccountNonLocked()).isTrue();
        assertThat(createdUser.isCredentialsNonExpired()).isTrue();
        assertThat(createdUser.isEnabled()).isTrue();
        verify(roleRepository, times(1)).findByName(RoleEnum.ADMIN);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateAdministrator_RoleNotFound() {
        // Given
        RegisterUserDto input = new RegisterUserDto();
        input.setEmail("admin@example.com");
        input.setPassword("password");
        input.setFullName("Admin");

        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.empty());

        // When
        User createdUser = userService.createAdministrator(input);

        // Then
        assertThat(createdUser).isNull();
        verify(roleRepository, times(1)).findByName(RoleEnum.ADMIN);
    }
}
