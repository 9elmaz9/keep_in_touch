package com.example.keep_in_touch.service;

import com.example.keep_in_touch.dto.EventDTO;
import com.example.keep_in_touch.entities.Event;

import java.util.List;

public interface EventService {
    void saveEvent(Event event);
    List<Event> findEventsByUserId(int userId);
    Event findEventById(Long eventId);
    void deleteEvent(Long  evenId);
    EventDTO getEventDTOById(Long eventId);
    Event saveEventDTO(EventDTO eventDTO);
}
