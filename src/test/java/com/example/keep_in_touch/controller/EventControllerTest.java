package com.example.keep_in_touch.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.example.keep_in_touch.dto.EventDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.Event;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.EventMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.EventService;
import com.example.keep_in_touch.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @Mock
    private ContactService contactService;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventController eventController;

    @Mock
    private Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserEvents() {
        User user = new User();
        user.setId(1);

        List<EventDTO> events = new ArrayList<>();
        events.add(new EventDTO());

        when(principal.getName()).thenReturn("user@example.com");
        when(userService.getUserByUsername("user@example.com")).thenReturn(user);
        when(eventService.findEventsByUserId(user.getId())).thenReturn(new ArrayList<>());
        when(eventMapper.toDTO(any())).thenReturn(new EventDTO());

        List<EventDTO> result = eventController.getUserEvents(principal);
        assertNotNull(result);
    }

    @Test
    public void testAddEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setTitle("Test Event");

        User user = new User();
        user.setId(1);

        Contact contact = new Contact();
        contact.setCid(1);

        Event event = new Event();
        event.setTitle("Test Event");

        when(principal.getName()).thenReturn("user@example.com");
        when(userService.getUserByUsername("user@example.com")).thenReturn(user);
        when(contactService.getContactById(1)).thenReturn(contact);
        when(eventMapper.toEntity(any(EventDTO.class))).thenReturn(event);

        String result = eventController.addEvent(eventDTO, 1L, principal);
        assertEquals("Event added successfully ", result);

    }
}

