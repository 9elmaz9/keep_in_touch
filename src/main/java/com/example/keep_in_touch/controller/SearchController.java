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
// Handles the searching of contacts and users based on a given name
//This controller handles search operations for both contacts and users, providing endpoints to retrieve relevant data based on a search query

@RestController
// Marks this class as a Spring controller that returns data directly in HTTP responses, typically in JSON format
//возвращает данные непосредственно в HTTP-ответе, обычно в формате JSON
//Это означает, что все методы внутри этого контроллера будут возвращать JSON или другие форматы данных, а не HTML-страницы
public class SearchController {

    // для работы с базой данных - Dependency injection
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ContactMapper contactMapper;


    /**
     *Searches for contacts by name for the logged-in user
     *  search: Ищет контакты по имени для авторизованного пользователя, преобразует их в DTO и возвращает в формате JSON.
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query,  // The search term extracted from the URL.
                                    Principal principal) {   // Provides details about the logged-in user.

        if (principal == null) { // If the user is not logged in, return a 401 Unauthorized response.
            return ResponseEntity.status(401).body("Unauthorized");
        }
        // Get the loggedin user
        User user = this.userRepository.getUserByUserName(principal.getName());

        // Search for contacts by name within the user's contacts- для поиска контактов по имени среди контактов текущего пользователя
        List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(query, user);
        // Convert contacts to DTOs
        List<ContactDTO> contactDTOs = contacts.stream()
                // Convert each contact to a ContactDTO using the contactMapper.
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
        // Return the list of contact DTOs
        return ResponseEntity.ok(contactDTOs);

    }


    /**
     * This method handles GET requests to search for users by name
     *  searchUserHandler: Ищет пользователей по имени, преобразует их в DTO и возвращает в формате JSON.
     */
    @GetMapping("/search-user{query}")
    public ResponseEntity<?> searchUserHandler(@PathVariable("query") String query,
                                               Principal principal) {

        // Check if the user is authenticated
        if (principal == null) {
            // If not authenticated, return a 401 Unauthorized response
            return ResponseEntity.status(401).body("Unauthorized");
        }
        //Get the logged-in user from db
        User user = this.userRepository.getUserByUserName(principal.getName());
        // Search for users whose names contain the query string
        List<User> users = this.userRepository.findByNameContaining(query);
        // Convert the list of users to a list of UserDTOs
        List<UserDTO> userDTOs = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        //Return the list of user DTOs and respponse ok
        return ResponseEntity.ok(userDTOs);
    }


}

