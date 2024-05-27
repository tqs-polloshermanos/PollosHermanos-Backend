package com.github.tqspolloshermanos.repositories;

import com.github.tqspolloshermanos.bootstrap.RoleSeeder;
import com.github.tqspolloshermanos.entities.Role;
import com.github.tqspolloshermanos.entities.RoleEnum;
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
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleSeeder roleSeeder;

    @BeforeEach
    void setUp() {
        roleSeeder.onApplicationEvent(null);
    }

    @Test
    void testFindByName_UserRole() {
        // When
        Optional<Role> foundRole = roleRepository.findByName(RoleEnum.USER);

        // Then
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getName()).isEqualTo(RoleEnum.USER);
        assertThat(foundRole.get().getDescription()).isEqualTo("Default user role");
    }

    @Test
    void testFindByName_AdminRole() {
        // When
        Optional<Role> foundRole = roleRepository.findByName(RoleEnum.ADMIN);

        // Then
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getName()).isEqualTo(RoleEnum.ADMIN);
        assertThat(foundRole.get().getDescription()).isEqualTo("Administrator role");
    }

    @Test
    void testFindByName_SuperAdminRole() {
        // When
        Optional<Role> foundRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);

        // Then
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getName()).isEqualTo(RoleEnum.SUPER_ADMIN);
        assertThat(foundRole.get().getDescription()).isEqualTo("Super Administrator role");
    }
}
