package com.example.keep_in_touch.controller;


import com.example.keep_in_touch.dto.EventDTO;
import com.example.keep_in_touch.mapper.EventMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.EventService;
import com.example.keep_in_touch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

//управляет операциями, связанными с событиями (Event).
// Этот контроллер предоставляет API для добавления, обновления, удаления и просмотра событий,
// связанных с контактами.
@RestController
@RequestMapping("/api/events")
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
    public String addEvent(@RequestBody EventDTO eventDTO,
                           @RequestParam Long contactId,
                           Principal principal){
        return  null;
    }




    @GetMapping("/user")
    public List<EventDTO> getUserEvents(Principal principal){
        return null;
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
