package com.example.keep_in_touch.controller;


import com.example.keep_in_touch.dto.ContactDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.ContactMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @Mock
    private UserService userService;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactController contactController;

    @Mock
    private Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserContacts() {
        User user = new User();
        user.setId(1);

        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        contacts.add(contact);

        when(principal.getName()).thenReturn("user@example.com");
        when(userService.getUserByUsername("user@example.com")).thenReturn(user);
        when(contactService.findContactsByUserId(user.getId())).thenReturn(contacts);
        when(contactMapper.toDTO(contact)).thenReturn(new ContactDTO());

        ResponseEntity<?> response = contactController.getUserContacts(principal);
        List<ContactDTO> result = (List<ContactDTO>) response.getBody();
        assertNotNull(result);
    }

    @Test
    public void testGetUserContacts_NoContacts() {
        User user = new User();
        user.setId(1);

        when(principal.getName()).thenReturn("user@example.com");
        when(userService.getUserByUsername("user@example.com")).thenReturn(user);
        when(contactService.findContactsByUserId(user.getId())).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = contactController.getUserContacts(principal);
        List<ContactDTO> result = (List<ContactDTO>) response.getBody();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUserContacts_WithContacts() {
        User user = new User();
        user.setId(1);

        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        contacts.add(contact);

        ContactDTO contactDTO = new ContactDTO();

        when(principal.getName()).thenReturn("user@example.com");
        when(userService.getUserByUsername("user@example.com")).thenReturn(user);
        when(contactService.findContactsByUserId(user.getId())).thenReturn(contacts);
        when(contactMapper.toDTO(contact)).thenReturn(contactDTO);

        ResponseEntity<?> response = contactController.getUserContacts(principal);
        List<ContactDTO> result = (List<ContactDTO>) response.getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contactDTO, result.get(0));
    }
}
