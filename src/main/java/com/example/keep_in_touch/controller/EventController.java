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

//управляет операциями, связанными с событиями (Event).
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



    //Метод addEvent для добавления события
    @PostMapping("/add")
    public String addEvent(@RequestBody EventDTO eventDTO, // dto содержащий данные нового события
                           @RequestParam Long contactId,  //Идентификатор контакта, связанного с этим событием
                           Principal principal){  ////Проверяется, аутентифицирован ли пользователь

        if(principal == null){
            return "Unauthorized";
        }
        User user = userService.getUserByUsername(principal.getName()); //Получаем текущего пользователя по имени из объекта Principal
        Event event = eventMapper.toEntity(eventDTO);
        event.setUser(user);//Преобразуем EventDTO в сущность Event и устанавливаем текущего пользователя и контакт, с которым связано событие

        Contact contact = contactService.getContactById(contactId.intValue());
        event.setContact(contact);

        eventService.saveEvent(event); //сохраняем событие в базе данных
        return  "Event added successfully "; // //Возвращаемое значение: Метод возвращает строку с сообщением о результате операции.
    }




    @GetMapping("/user")
    //Метод getUserEvents для получения событий пользователя
    //Проверка, аутентифицирован ли пользователь.
    //Получение событий, связанных с пользователем, преобразование их в DTO и возврат списка событий
    public List<EventDTO> getUserEvents(Principal principal){
        if ( principal == null){
            return  null;
        }
        User user = userService.getUserByUsername(principal.getName());
        return eventService.findEventsByUserId(user.getId()).stream()
                .map(event -> {
                    EventDTO dto = eventMapper.toDTO(event);
                    dto.setContactName(event.getContact().getName());
                    return dto;
                })
                .collect(Collectors.toList()); //возвращает значение- список обьектов EventDTO
    }


    @GetMapping("/show-events")
    public String showEvents(Model model, Principal principal){
        return  null;
    }

    @PutMapping("/update/{id}")
    public String updateEvent(@PathVariable Long id,
                              @RequestBody EventDTO eventDetails,
                              @RequestParam Long contactId,
                              Principal principal){
        return null;
    }


    @DeleteMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id, Principal principal){
        return null;

    }
}
