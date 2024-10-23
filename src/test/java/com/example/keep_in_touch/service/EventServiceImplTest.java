package com.example.keep_in_touch.service;

import com.example.keep_in_touch.dto.EventDTO;
import com.example.keep_in_touch.entities.Event;
import com.example.keep_in_touch.mapper.EventMapper;
import com.example.keep_in_touch.repository.EventRepository;
import com.example.keep_in_touch.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event event;
    private EventDTO eventDTO;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1L);
        event.setTitle("Test Event");

        eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setTitle("Test Event");
    }

    @Test
    void testSaveEvent() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        eventService.saveEvent(event);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testFindEventsByUserId() {
        when(eventRepository.findByUserId(anyInt())).thenReturn(List.of(event));
        List<Event> events = eventService.findEventsByUserId(1);
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals("Test Event", events.get(0).getTitle());
    }

    @Test
    void testFindEventById() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        Event foundEvent = eventService.findEventById(1L);
        assertNotNull(foundEvent);
        assertEquals("Test Event", foundEvent.getTitle());
    }

    @Test
    void testDeleteEvent() {
        doNothing().when(eventRepository).deleteById(anyLong());
        eventService.deleteEvent(1L);
        verify(eventRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetEventDTOById() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(eventMapper.toDTO(any(Event.class))).thenReturn(eventDTO);
        EventDTO foundEventDTO = eventService.getEventDTOById(1L);
        assertNotNull(foundEventDTO);
        assertEquals("Test Event", foundEventDTO.getTitle());
    }

    @Test
    void testSaveEventDTO() {
        when(eventMapper.toEntity(any(EventDTO.class))).thenReturn(event);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        Event savedEvent = eventService.saveEventDTO(eventDTO);
        assertNotNull(savedEvent);
        assertEquals("Test Event", savedEvent.getTitle());
    }
}


