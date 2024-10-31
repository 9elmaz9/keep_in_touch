package com.example.keep_in_touch.dao;

import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.Event;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventRepositoryTest {

    @Mock
    private EventRepository eventRepository;

    private User testUser;
    private Contact testContact;
    private Event testEvent1;
    private Event testEvent2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1);
        testUser.setName("Test User");

        testContact = new Contact();
        testContact.setName("Test Contact");

        testEvent1 = new Event();
        testEvent1.setTitle("Event 1 Title");
        testEvent1.setStart(new Date());
        testEvent1.setNote("Event 1 Note");
        testEvent1.setUser(testUser);
        testEvent1.setContact(testContact);

        testEvent2 = new Event();
        testEvent2.setTitle("Event 2 Title");
        testEvent2.setStart(new Date());
        testEvent2.setNote("Event 2 Note");
        testEvent2.setUser(testUser);
        testEvent2.setContact(testContact);
    }

    @Test
    public void testFindByUserId() {
        when(eventRepository.findByUserId(testUser.getId())).thenReturn(Arrays.asList(testEvent1, testEvent2));

        List<Event> events = eventRepository.findByUserId(testUser.getId());

        assertThat(events).isNotNull();
        assertThat(events.size()).isEqualTo(2);
        assertThat(events.get(0).getTitle()).isEqualTo("Event 1 Title");
        assertThat(events.get(1).getTitle()).isEqualTo("Event 2 Title");
    }

    @Test
    public void testFindByUserId_NoEvents() {
        when(eventRepository.findByUserId(testUser.getId())).thenReturn(Collections.emptyList());

        List<Event> events = eventRepository.findByUserId(testUser.getId());

        assertThat(events).isNotNull();
        assertThat(events.size()).isEqualTo(0);
    }

    @Test
    public void testFindByUserId_EmptyList() {
        when(eventRepository.findByUserId(testUser.getId())).thenReturn(Collections.emptyList());

        List<Event> events = eventRepository.findByUserId(testUser.getId());

        assertThat(events).isNotNull();
        assertThat(events.isEmpty()).isTrue();
    }

    @Test
    public void testFindByUserId_Exception() {
        when(eventRepository.findByUserId(testUser.getId())).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventRepository.findByUserId(testUser.getId());
        });

        assertThat(exception.getMessage()).isEqualTo("Database error");
    }
}
