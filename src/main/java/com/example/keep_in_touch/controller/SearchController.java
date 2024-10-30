package com.example.keep_in_touch.controller;


import com.example.keep_in_touch.dto.ContactDTO;
import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.ContactMapper;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.ContactRepository;
import com.example.keep_in_touch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
// Handles the searching of contacts and users based on a given name -for both contacts and users,поиск в приложении

@RestController
//Это означает, что все методы внутри этого контроллера будут возвращать JSON или другие форматы данных, а не HTML-страницы
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ContactMapper contactMapper;


    //Ищет  все контакты по имени для авторизованного пользователя, преобразует их в DTO и возвращает в формате JSON.
    @GetMapping("/search/{query}")   //строка
    public ResponseEntity<?> search(@PathVariable("query") String query,
                                    Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = this.userRepository.getUserByUserName(principal.getName());
        List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(query, user); // строка поиска в имени
        // Convert contacts to DTOs
        List<ContactDTO> contactDTOs = contacts
                .stream()
                // Convert each contact to a ContactDTO
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(contactDTOs); // list json contacr dto

    }


    //Ищет пользователей по имени, преобразует их в DTO и возвращает в формате JSON. конткт текущего пользователя
    @GetMapping("/search-user/{query}")
    public ResponseEntity<?> searchUserHandler(@PathVariable("query") String query,
                                               Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        User user = this.userRepository.getUserByUserName(principal.getName());
        List<User> users = this.userRepository.findByNameContaining(query);
        List<UserDTO> userDTOs = users
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }


}

