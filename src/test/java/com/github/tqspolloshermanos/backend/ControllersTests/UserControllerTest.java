package com.github.tqspolloshermanos.backend.ControllersTests;

import com.github.tqspolloshermanos.backend.Entities.ERole;
import com.github.tqspolloshermanos.backend.Entities.User;
import com.github.tqspolloshermanos.backend.Services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterUser() throws Exception {
        User user = new User("test@example.com", "password123", ERole.CUSTOMER);

        Mockito.when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"email\": \"test@example.com\", \"password\": \"password123\", \"role\": \"CUSTOMER\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testLoginUser() throws Exception {
        User user = new User("test@example.com", "password123", ERole.CUSTOMER);

        Mockito.when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/users/login")
                        .contentType("application/json")
                        .content("{\"email\": \"test@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isOk());
    }
}
