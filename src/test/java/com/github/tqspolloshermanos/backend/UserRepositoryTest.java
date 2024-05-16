package com.github.tqspolloshermanos.backend;

import com.github.tqspolloshermanos.backend.Entities.User;
import com.github.tqspolloshermanos.backend.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(false) // Disable rollback to persist data for testing
    public void testSaveUser() {
        // Create a new user
        User user = new User("test@example.com", "password123");

        // Save the user
        User savedUser = userRepository.save(user);

        // Verify that the user has been saved
        Optional<User> retrievedUserOptional = userRepository.findById(savedUser.getUserId());
        assertTrue(retrievedUserOptional.isPresent(), "User should be saved");
        User retrievedUser = retrievedUserOptional.get();
        assertEquals(user.getEmail(), retrievedUser.getEmail(), "Email should match");
        assertEquals(user.getPassword(), retrievedUser.getPassword(), "Password should match");
    }

    @Test
    public void testFindUserByEmail() {
        // Create a new user
        User user = new User("test1@example.com", "password123");

        // Save the user
        userRepository.save(user);

        // Find the user by email
        Optional<User> foundUserOptional = userRepository.findByEmail("test1@example.com");

        // Verify that the user has been found
        assertTrue(foundUserOptional.isPresent(), "User should be found by email");
        User foundUser = foundUserOptional.get();
        assertEquals(user.getEmail(), foundUser.getEmail(), "Email should match");
        assertEquals(user.getPassword(), foundUser.getPassword(), "Password should match");
    }
}
