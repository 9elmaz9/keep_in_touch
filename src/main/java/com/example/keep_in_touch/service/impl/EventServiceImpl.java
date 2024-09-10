package com.example.keep_in_touch.service.impl;

import com.example.keep_in_touch.dto.EventDTO;
import com.example.keep_in_touch.entities.Event;
import com.example.keep_in_touch.mapper.EventMapper;
import com.example.keep_in_touch.repository.EventRepository;
import com.example.keep_in_touch.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Реализация интерфейса EventService для управления сущностями Event
@Service
public class
EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;


    // Сохраняет entity события в базе данных
    @Override
    public void saveEvent(Event event) {
        eventRepository.save(event);

    }


    //Находит список событий для конкретного пользователя по его ID
    @Override
    public List<Event> findEventsByUserId(int userId) {
        return eventRepository.findByUserId(userId);
    }

    //Находит событие по его ID
    @Override
    public Event findEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }


    //Удаляет событие по его ID
    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);

    }


    //Возвращает объект EventDTO по его ID
    @Override
    public EventDTO getEventDTOById(Long eventId) {
        Event event = findEventById(eventId);
        return event != null ? eventMapper.toDTO(event) : null;
    }

    //Сохраняет объект EventDTO, преобразует его в сущность Event и возвращает сохраненную сущность
    @Override
    public Event saveEventDTO(EventDTO eventDTO) {
        Event event = eventMapper.toEntity(eventDTO);
        return eventRepository.save(event);

    }
}
