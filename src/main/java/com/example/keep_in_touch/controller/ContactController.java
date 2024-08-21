package com.example.keep_in_touch.controller;


//Контроллер предоставляет REST API для управления контактами, включая добавление, редактирование, удаление и просмотр контактов.

import com.example.keep_in_touch.dto.ContactDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.ContactMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


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
    /**This method handles GET requests to retrieve the user's contacts
     * Retrieves a list of contacts for the authenticated user*/
    @GetMapping("/user")
    public ResponseEntity<?> getUserContacts (Principal principal){
        // Check if the user is logged in
        if(principal == null){
            // If not logged in, send a 401 Unauthorized response
            return ResponseEntity.status(401).body("Unauthorized");
        }
        // Find the logged-in user using their username
        User user = userService.getUserByUsername(principal.getName());

        // Get the user's contacts and convert them to ContactDTOs
        List<ContactDTO> contacts = contactService.findContactsByUserId(user.getId()).stream()
                .map(contactMapper::toDTO) // Convert each contact to a simpler format (DTO)
                .collect(Collectors.toList());

        // Send back the list of contacts with a 200 OK status
        return ResponseEntity.ok(contacts);
    }



    //2
    /** Handles requests to update a contact by its ID */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable int id,
                                           @RequestBody Contact contact,
                                           Principal principal){
        //check if the user logged in
        if(principal == null){
            //if not
            return ResponseEntity.status(401).body("Unauthorized");
        }
        // get the loggedin user info
        User user = userService.getUserByUsername(principal.getName());
        // check if the contact belongs to the loggedin user
        if(contactService.getContactById(id).getUser().getId() != user.getId()){
            //if not - return
            return ResponseEntity.status(403).body("Forbidden");
        }
        //save updated info
        contactService.saveContact(contact);
        //then return a sms about updating
        return ResponseEntity.ok("Contact updated successfully");
    }

    //3
    //This handles requests to delete a contact by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact (@PathVariable int id,
                                            Principal principal){
        //check if the user is loggedin
        if(principal == null){
            //if not : display this
            return ResponseEntity.status(401).body("Unauthorized");
        }
        // get the logged-in user's info
        User user = userService.getUserByUsername(principal.getName());
        //find by id
        Contact contact = contactService.getContactById(id);
        //check if contact belong and exist
        if(contact == null || contact.getUser().getId() != user.getId()){
            //if contect nor found or not belong to user - give this
            return ResponseEntity.status(404).body("Contact not found or not authorized to delete this contact");
        }
        contactService.deleteContact(contact); // del

        // message   contact was deleted
        return ResponseEntity.ok("Contact deleted successfully");
    }
}
