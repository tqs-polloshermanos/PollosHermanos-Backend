package com.github.tqspolloshermanos.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void testUserEntityMethods() {
        // Create a Role object
        Role role = new Role();
        role.setId(1);
        role.setName(RoleEnum.USER);
        role.setDescription("User role");

        // Create a User object
        User user = new User();

        // Set values
        user.setId(1);
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRole(role);

        // Test getters
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getFullName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertEquals(role, user.getRole());
    }
}
