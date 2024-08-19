package com.example.keep_in_touch.controller;


// Контроллер предоставляет REST API для управления контактами, включая добавление, редактирование, удаление и просмотр контактов.

import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.mapper.ContactMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactMapper contactMapper;


    //1
    public ResponseEntity<?> getUserContacts (Principal principal){
        return null;
    }

    //2
    public ResponseEntity<?> updateContact(@PathVariable int id,
                                           @RequestBody Contact contact,
                                           Principal principal){
        return null;
    }

    //3
    public ResponseEntity<?> deleteContact (@PathVariable int id,
                                            Principal principal){
        return null;
    }
}
