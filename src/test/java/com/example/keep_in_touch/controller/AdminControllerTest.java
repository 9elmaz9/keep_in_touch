package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.ContactRepository;
import com.example.keep_in_touch.repository.UserRepository;
import com.example.keep_in_touch.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;
    private User adminUser;
    private UserDTO adminUserDTO;
    private User user;
    private UserDTO userDTO;
    private List<User> users;
    private List<Contact> contacts;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        adminUser = new User();
        adminUser.setId(1);
        adminUser.setName("Admin User");
        adminUser.setEmail("admin@example.com");
        adminUser.setRole("ROLE_ADMIN");

        adminUserDTO = new UserDTO();
        adminUserDTO.setId(1);
        adminUserDTO.setName("Admin User");
        adminUserDTO.setEmail("admin@example.com");

        user = new User();
        user.setId(2);
        user.setName("Test User");
        user.setEmail("testuser@example.com");

        userDTO = new UserDTO();
        userDTO.setId(2);
        userDTO.setName("Test User");
        userDTO.setEmail("testuser@example.com");

        users = new ArrayList<>();
        users.add(user);

        contacts = new ArrayList<>();

        lenient().when(userRepository.findByEmail(anyString())).thenReturn(adminUser);
        lenient().when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(users));
        lenient().when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        lenient().when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);
        lenient().when(userMapper.toDTO(adminUser)).thenReturn(adminUserDTO);
    }

    @Test
    void testGetDashboard() throws Exception {
        mockMvc.perform(get("/admin/dashboard/0")
                        .principal(() -> "admin@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin_dashboard"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attribute("user", adminUserDTO));
    }

    @Test
    void testGetUserProfile() throws Exception {
        mockMvc.perform(get("/admin/user-profile/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user_profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", userDTO));
    }

    @Test
    void testDeleteUserHandler() throws Exception {
        when(contactRepository.findByUser(any(User.class))).thenReturn(contacts);
        when(userService.getUserByUsername(anyString())).thenReturn(adminUser);

        mockMvc.perform(get("/admin/user-delete/2")
                        .principal(() -> "admin@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard/0"));

        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void testAdminDashboard() throws Exception {
        mockMvc.perform(get("/admin/dashboard")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_dashboard"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"));
    }
}
