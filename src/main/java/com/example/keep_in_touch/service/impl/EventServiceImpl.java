package com.example.keep_in_touch.service.impl;

import com.example.keep_in_touch.dto.EventDTO;
import com.example.keep_in_touch.entities.Event;
import com.example.keep_in_touch.mapper.EventMapper;
import com.example.keep_in_touch.repository.EventRepository;
import com.example.keep_in_touch.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EventServiceImpl implements EventService {
 // @Autowired
 // private EventRepository eventRepository;
 // @Autowired
 // private EventMapper eventMapper;

    @Override
    public void saveEvent(Event event) {

    }

    @Override
    public List<Event> findEventsByUserId(int userId) {
        return null;
    }

    @Override
    public Event findEventById(Long eventId) {
        return null;
    }

    @Override
    public void deleteEvent(Long evenId) {

    }

    @Override
    public EventDTO getEventDTOById(Long eventId) {
        return null;
    }

    @Override
    public Event saveEventDTO(EventDTO eventDTO) {
        return null;
    }
}
