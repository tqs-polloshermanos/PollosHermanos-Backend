package com.github.tqspolloshermanos.backend;

import com.github.tqspolloshermanos.backend.Entities.RoleType;
import com.github.tqspolloshermanos.backend.Entities.User;
import com.github.tqspolloshermanos.backend.Repositories.UserRepository;
import com.github.tqspolloshermanos.backend.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        List<User> userList = Arrays.asList(
                new User("user1@example.com", "password1", RoleType.CUSTOMER),
                new User("user2@example.com", "password2", RoleType.ADMIN)
        );

        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals(userList.get(0).getEmail(), users.get(0).getEmail());
        assertEquals(userList.get(1).getEmail(), users.get(1).getEmail());
    }

    @Test
    void getUserById() {
        User user = new User("user1@example.com", "password", RoleType.CUSTOMER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> optionalUser = userService.getUserById(1L);

        assertEquals(user.getEmail(), optionalUser.get().getEmail());
    }

    @Test
    void saveUser() {
        User user = new User("user@example.com", "password", RoleType.CUSTOMER);

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(user.getEmail(), savedUser.getEmail());
    }
}
