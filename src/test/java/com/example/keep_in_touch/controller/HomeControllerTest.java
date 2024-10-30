package com.example.keep_in_touch.controller;


import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void testHomeWithoutPrincipal() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", (Object) null))
                .andExpect(view().name("home"));
    }

    @Test
    public void testHomeWithPrincipal() throws Exception {
        Principal principal = () -> "testuser";
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test User");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("testuser@example.com");

        when(userRepository.getUserByUserName("testuser")).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        mockMvc.perform(get("/").principal(principal))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userDTO))
                .andExpect(view().name("home"));
    }

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/signin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userLogin"))
                .andExpect(view().name("login"));
    }

    @Test
    public void testHandleRegistrationSuccess() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("password");

        User user = new User();
        user.setUsername("testuser");

        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/do-register")
                        .param("agreement", "true")
                        .flashAttr("user", userDTO))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("signup"));
    }

    @Test
    public void testHandleRegistrationFailure() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("password");

        mockMvc.perform(post("/do-register")
                        .param("agreement", "false")
                        .flashAttr("user", userDTO))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("signup"));
    }
}
