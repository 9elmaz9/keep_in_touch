package com.example.keep_in_touch.controller;


import com.example.keep_in_touch.dto.EventDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.Event;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.EventMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.EventService;
import com.example.keep_in_touch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

// This controller handles operations related to events (Event).
// Этот контроллер предоставляет API для добавления, обновления, удаления и просмотра событий, связанных с контактами.
@RestController
@RequestMapping("/api/events") // Определяет базовый URL для всех маршрутов
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private EventMapper eventMapper;


    @PostMapping("/add")
    public String addEvent(@RequestBody EventDTO eventDTO, // dto содержащий данные нового события json
                           @RequestParam Long contactId,  //Идентификатор контакта, связанного с этим событием
                           Principal principal) {  //Проверяется, аутентифицирован ли пользователь

        if (principal == null) {
            return "Unauthorized";
        }
        User user = userService.getUserByUsername(principal.getName());
        Event event = eventMapper.toEntity(eventDTO);
        event.setUser(user);

        // Fetch the contact associated with the event using the contact ID
        Contact contact = contactService.getContactById(contactId.intValue());
        event.setContact(contact);


        // Save the event to the database
        eventService.saveEvent(event);
        return "Event added successfully ";
    }


    @GetMapping("/user")
    //П cписок событий для аутентифицированна пользователя   преобразование их в DTO и возврат списка
    public List<EventDTO> getUserEvents(Principal principal) {

        if (principal == null) {
            return null;
        }

        User user = userService.getUserByUsername(principal.getName());
        List<EventDTO> events = eventService.findEventsByUserId(user.getId()).stream()

        // поток в мап и там кажое событие в дто и ему имя дает
                .map(event -> {
                    EventDTO dto = eventMapper.toDTO(event);
                    dto.setContactName(event.getContact().getName());
                    return dto;
                })
                .collect(Collectors.toList()); // собираем список в лист из дто
        System.out.println("Vents for user" + principal.getName()+ ":" + events);

        return events;
    }


    //список событий для автори пользова
    @GetMapping("/show-events")
    public String showEvents(Model model,
                             Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByUsername(principal.getName());

        // излекаем лист из бз
        List<EventDTO> events = eventService.findEventsByUserId(user.getId())
                .stream()
                .map(event -> {
                    EventDTO dto = eventMapper.toDTO(event);
                    dto.setContactName(event.getContact().getName());
                    return dto;
                })
                .collect(Collectors.toList());

        model.addAttribute("events", events);// список событиый в модель

        return "show_events";
    }


    @PutMapping("/update/{id}")
    //Метод updateEvent для обновления события-Updates an existing event
    public String updateEvent(@PathVariable Long id,
                              @RequestBody EventDTO eventDetails, // формат джейсон
                              @RequestParam Long contactId,
                              Principal principal) {

        if (principal == null) {
            return "Unauthorized";
        }

        Event event = eventService.findEventById(id);
        // проверяя права пользователя
        if (event == null || !event.getUser().getEmail().equals(principal.getName())) {
            return "Event not found or you do not have permission to update this event";
        }

        Contact contact = contactService.getContactById(contactId.intValue());

        // Update the event details
        event.setTitle(eventDetails.getTitle());
        event.setStart(eventDetails.getStart());
        event.setContact(contact);
        event.setNote(eventDetails.getNote());

        eventService.saveEvent(event);

        return "Event updated successfully!";
    }


    @DeleteMapping("/delete/{id}")
    // Deletes an existing event
    public String deleteEvent(@PathVariable Long id,
                              Principal principal) {

        if (principal == null) {
            return "Unauthorized";
        }
        Event event = eventService.findEventById(id);

        if (event == null || !event.getUser().getEmail().equals(principal.getName())) {
            return "Event not found or you do not have permission to update this event";
        }
        eventService.deleteEvent(id);

        return "Event deleted successfully!";

    }
}
