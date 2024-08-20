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

@RestController  // Marks this class as a Spring controller that returns data directly in HTTP responses, typically in JSON format
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


    //Метод search для поиска контактов-Searches for contacts by name for the logged-in user
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search (@PathVariable("query") String query,  // The search term extracted from the URL.
                                     Principal principal){   // Provides details about the logged-in user.

        if ( principal == null) { // If the user is not logged in, return a 401 Unauthorized response.
            return ResponseEntity.status(401).body("Unauthorized");
        }
        // Get the logged-in user
        User user = this.userRepository.getUserByUserName(principal.getName());

        // Search for contacts by name within the user's contacts
        List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(query, user);
        // Convert contacts to DTOs
        List<ContactDTO> contactDTOs = contacts.stream()
                // Convert each contact to a ContactDTO using the contactMapper.
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
        // Return the list of contact DTOs

        return ResponseEntity.ok(contactDTOs);

}
    @GetMapping("/search-user{query}") // where {query} is the search term
    public ResponseEntity<?> searchUserHandler(@PathVariable("query")String query,
                                               Principal principal){
        // If the user is not logged in, return a 401 Unauthorized response
        if(principal == null) {
            return  ResponseEntity.status(401).body("<unauthorized");
        }
        //Get the logged-in user
        User user = this.userRepository.getUserByUserName(principal.getName());
        //SEarch for users by name
        List<User> users = this.userRepository.findByNameContaining(query);
        //COnvert users to DTOs
        List<UserDTO> userDTOs =users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        //Return the list of user DTOs
        return ResponseEntity.ok(userDTOs);
    }


}



/**
 * search: Searches for contacts by name for the authenticated user. It converts the contacts to DTOs and returns them in a JSON format.
 *Finds contacts that match the search term for the currently logged-in user. It converts these contacts into a simpler format (DTO) and sends them back as a response.
 *
 * searchUserHandler: Finds users that match the search term. It converts these users into DTOs and sends them back as a response.
 *  Searches for users by name, converts them to DTOs, and returns them in a JSON format
 *
 * */