package com.github.tqspolloshermanos.repositories;

import com.github.tqspolloshermanos.bootstrap.RoleSeeder;
import com.github.tqspolloshermanos.entities.Role;
import com.github.tqspolloshermanos.entities.RoleEnum;
import com.github.tqspolloshermanos.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(RoleSeeder.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleSeeder roleSeeder;

    @BeforeEach
    public void setUp() {
        roleSeeder.onApplicationEvent(null);
        // Ensure roles are seeded
        Role userRole = roleRepository.findByName(RoleEnum.USER).orElseThrow();
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN).orElseThrow();
        Role superAdminRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN).orElseThrow();

        // Clear existing users and add test users
        userRepository.deleteAll();
        User user1 = new User().setFullName("John Doe").setEmail("john@example.com").setPassword("password").setRole(userRole);
        User user2 = new User().setFullName("Jane Doe").setEmail("jane@example.com").setPassword("password").setRole(adminRole);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void testFindByEmail_UserExists() {
        // When
        Optional<User> foundUser = userRepository.findByEmail("john@example.com");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("john@example.com");
        assertThat(foundUser.get().getFullName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindByEmail_UserDoesNotExist() {
        // When
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertThat(foundUser).isNotPresent();
    }

    @Test
    public void testSaveUser() {
        // Given
        Role userRole = roleRepository.findByName(RoleEnum.USER).orElseThrow();
        User user = new User().setFullName("New User").setEmail("newuser@example.com").setPassword("newpassword").setRole(userRole);

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");
        assertThat(savedUser.getFullName()).isEqualTo("New User");
    }

    @Test
    public void testDeleteUser() {
        // Given
        Optional<User> foundUser = userRepository.findByEmail("jane@example.com");
        assertThat(foundUser).isPresent();

        // When
        userRepository.delete(foundUser.get());

        // Then
        Optional<User> deletedUser = userRepository.findByEmail("jane@example.com");
        assertThat(deletedUser).isNotPresent();
    }
}
