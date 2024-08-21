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


    //Метод addEvent для добавления события- Adds a new event linked to a contact
    @PostMapping("/add")
    public String addEvent(@RequestBody EventDTO eventDTO, // dto содержащий данные нового события
                           @RequestParam Long contactId,  //Идентификатор контакта, связанного с этим событием
                           Principal principal) {  ////Проверяется, аутентифицирован ли пользователь

        if (principal == null) {
            return "Unauthorized"; //If the user is not authenticated, return an unauthorized message
        }
        // Retrieve the current user based on the authenticated user's name
        User user = userService.getUserByUsername(principal.getName()); //Получаем текущего пользователя по имени из объекта Principal
        // Convert the EventDTO to an Event entity and set the user and contact details
        Event event = eventMapper.toEntity(eventDTO);
        event.setUser(user);

        // Fetch the contact associated with the event using the contact ID
        Contact contact = contactService.getContactById(contactId.intValue());
        event.setContact(contact);


        // Save the event to the database
        eventService.saveEvent(event);
        return "Event added successfully "; // Метод возвращает строку с сообщением о результате операции.
    }


    @GetMapping("/user")
    //Метод getUserEvents для получения событий пользователя-Retrieves a list of events for the authenticated user
    //Проверка, аутентифицирован ли пользователь.
    //Получение событий, связанных с пользователем, преобразование их в DTO и возврат списка событий
    public List<EventDTO> getUserEvents(Principal principal) {

        if (principal == null) {
            return null;
        }
        // Retrieve the current user based on the authenticated user's name
        User user = userService.getUserByUsername(principal.getName());
        // Fetch and map the events associated with the user to a list of EventDTOs
        return eventService.findEventsByUserId(user.getId()).stream()
                .map(event -> {
                    EventDTO dto = eventMapper.toDTO(event);
                    dto.setContactName(event.getContact().getName()); // Set the contact name for the event
                    return dto;
                })
                .collect(Collectors.toList()); // Return the list of EventDTOs
    }


    @GetMapping("/show-events")  //GET-запросы по пути /api/events/show-events
    public String showEvents(Model model,
                             Principal principal) {
        //Displays a list of events for the authenticated user
        //Если пользователь не аутентифицирован, происходит редирект на страницу входа.
        //События пользователя извлекаются и добавляются в модель для отображения на странице show_events

        if ( principal == null){
            return  "redirect:/login"; // If the user is not authenticated, redirect to the login page
        }
        // Retrieve the current user based on the authenticated user's name
        User user = userService.getUserByUsername(principal.getName());

        // Fetch and map the events associated with the user to a list of EventDTOs
        List<EventDTO> events = eventService.findEventsByUserId(user.getId()).stream()
                .map(event -> {
                    EventDTO dto = eventMapper.toDTO(event);
                    dto.setContactName(event.getContact().getName());
                    return dto;
                })
                .collect(Collectors.toList());
        // Add the list of events to the model
        model.addAttribute("events",events);
        // Return the view name to display the events
        return "show_events";
    }


    //Метод обрабатывает PUT-запросы по пути /api/events/update/{id}, где {id} — идентификатор события
    @PutMapping("/update/{id}")
    //Метод updateEvent для обновления события-Updates an existing event
    //Логика метода:
    // Проверка аутентификации.
    // Проверка, принадлежит ли событие текущему пользователю.
    // Обновление информации о событии и сохранение изменений.
    public String updateEvent(@PathVariable Long id,
                              @RequestBody EventDTO eventDetails,
                              @RequestParam Long contactId,
                              Principal principal) {

        if ( principal ==null){
            return "Unauthorized";
        }

        // Find the event by its ID
        Event event = eventService.findEventById(id);
        // Check if the event exists and if the authenticated user is authorized to update it
        if ( event == null || !event.getUser().getEmail().equals(principal.getName())){
            return "Event not found or you do not have permission to update this event";
        }

        // Find the contact associated with the event
        Contact contact = contactService.getContactById(contactId.intValue());

        // Update the event details
        event.setTitle(eventDetails.getTitle());
        event.setStart(eventDetails.getStart());
        event.setContact(contact);
        event.setNote(eventDetails.getNote());

        // Save the updated event to the database
        eventService.saveEvent(event);
        // Return a success message
        return "Event updated successfully!";
    }


    @DeleteMapping("/delete/{id}")
    // Deletes an existing event
    //Логика метода:
    //Проверка аутентификации.
    //Проверка, принадлежит ли событие текущему пользователю.
    //Удаление события из базы данных.
    public String deleteEvent(@PathVariable Long id, //event id
                              Principal principal) {

        if(principal == null){
            return "Unauthorized";
        }
        // Find the event by its ID
        Event event = eventService.findEventById(id);
        // Check if the event exists and if the authenticated user is authorized to delete it
        if ( event == null || !event.getUser().getEmail().equals(principal.getName())){
            return "Event not found or you do not have permission to update this event";
        }
        // Delete the event from the database
        eventService.deleteEvent(id);
        // Return a success message
        return "Event deleted successfully!";

    }
}
