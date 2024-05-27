package com.github.tqspolloshermanos.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleTest {

    @Test
    void testRoleEntityMethods() {
        // Create a Role object
        Role role = new Role();

        // Set values
        role.setId(1);
        role.setName(RoleEnum.ADMIN);
        role.setDescription("Admin role");
        role.setCreatedAt(new Date());
        role.setUpdatedAt(new Date());

        // Test getters
        assertEquals(1, role.getId());
        assertEquals(RoleEnum.ADMIN, role.getName());
        assertEquals("Admin role", role.getDescription());
        assertNotNull(role.getCreatedAt());
        assertNotNull(role.getUpdatedAt());
    }
}
