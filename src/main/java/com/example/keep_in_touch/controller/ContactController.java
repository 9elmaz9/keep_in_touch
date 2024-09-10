package com.example.keep_in_touch.controller;


//Контроллер предоставляет REST API для управления контактами, включая добавление, редактирование, удаление и просмотр контактов.json возвраает странциы

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


    //  список контактов пользовтаеля если автор
    @GetMapping("/user")
    public ResponseEntity<?> getUserContacts(Principal principal) {
        if (principal == null) {
            // If not
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userService.getUserByUsername(principal.getName());
        List<ContactDTO> contacts = contactService.findContactsByUserId(user.getId())
                .stream()
                .map(contactMapper::toDTO) // Convert each contact to a  format DTO
                .collect(Collectors.toList());

        return ResponseEntity.ok(contacts);
    }


    // Handles requests to update a contact by its ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable int id,
                                           @RequestBody Contact contact,
                                           Principal principal) {
        if (principal == null) {
            //if not
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userService.getUserByUsername(principal.getName());

        // check if the contact belongs to the loggedin user
        if (contactService.getContactById(id).getUser().getId() != user.getId()) {
            //if not
            return ResponseEntity.status(403).body("Forbidden");
        }
        contactService.saveContact(contact);
        return ResponseEntity.ok("Contact updated successfully");
    }


    //This handles requests to delete a contact by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable int id,
                                           Principal principal) {
        if (principal == null) {
            //if not
            return ResponseEntity.status(401).body("Unauthorized");
        }
        // get the logged-in user's info
        User user = userService.getUserByUsername(principal.getName());
        //find by id
        Contact contact = contactService.getContactById(id);

        if (contact == null || contact.getUser().getId() != user.getId()) {
            return ResponseEntity.status(404).body("Contact not found or not authorized to delete this contact");
        }
        contactService.deleteContact(contact); // del

        return ResponseEntity.ok("Contact deleted successfully");
    }
}
