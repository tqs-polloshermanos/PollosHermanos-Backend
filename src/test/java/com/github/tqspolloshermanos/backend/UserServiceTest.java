package com.github.tqspolloshermanos.backend;

import com.github.tqspolloshermanos.backend.Entities.ERole;
import com.github.tqspolloshermanos.backend.Entities.User;
import com.github.tqspolloshermanos.backend.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User("test@example.com", "password123", ERole.CUSTOMER);
        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getRole(), savedUser.getRole());
    }

    @Test
    void testFindById_ExistingUser() {
        User user = new User("test@example.com", "password123", ERole.CUSTOMER);
        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(savedUser, foundUser.get());
    }

    @Test
    void testFindById_NonExistingUser() {
        Optional<User> foundUser = userRepository.findById(100L); // Assuming ID 100 does not exist

        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByEmail_ExistingUser() {
        User user = new User("test@example.com", "password123", ERole.CUSTOMER);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void testFindByEmail_NonExistingUser() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void testDeleteById_ExistingUser() {
        User user = new User("test@example.com", "password123", ERole.CUSTOMER);
        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        assertFalse(userRepository.existsById(savedUser.getId()));
    }
}
