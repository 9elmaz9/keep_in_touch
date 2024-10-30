package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.dto.ContactDTO;
import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.ContactMapper;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.ContactRepository;
import com.example.keep_in_touch.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private ContactMapper contactMapper;

    @InjectMocks
    private SearchController searchController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testSearchContacts() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        Contact contact = new Contact();
        contact.setName("Elmaz Dzhelianchyk");
        List<Contact> contacts = Arrays.asList(contact);

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName("Elmaz Dzhelianchyk");

        when(userRepository.getUserByUserName(anyString())).thenReturn(user);
        when(contactRepository.findByNameContainingAndUser(anyString(), eq(user))).thenReturn(contacts);
        when(contactMapper.toDTO(contact)).thenReturn(contactDTO);

        mockMvc.perform(get("/search/Elmaz")
                        .principal(() -> "testuser"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name':'Elmaz Dzhelianchyk'}]"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testSearchUsers() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        User searchedUser = new User();
        searchedUser.setName("Elmaz Dzhelianchyk");
        List<User> users = Arrays.asList(searchedUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Elmaz Dzhelianchyk");

        when(userRepository.getUserByUserName(anyString())).thenReturn(user);
        when(userRepository.findByNameContaining(anyString())).thenReturn(users);
        when(userMapper.toDTO(searchedUser)).thenReturn(userDTO);

        mockMvc.perform(get("/search-user/Elmaz") // Проверьте правильность маршрута
                        .principal(() -> "testuser"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Elmaz Dzhelianchyk\"}]")); // Убедитесь, что формат JSON правильный
    }


}
