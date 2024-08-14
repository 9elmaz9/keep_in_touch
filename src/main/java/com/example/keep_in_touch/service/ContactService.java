package com.example.keep_in_touch.service;

import com.example.keep_in_touch.dto.ContactDTO;
import com.example.keep_in_touch.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    void saveContact(Contact contact);
    Page<Contact> findContactsByUserId(int userId, Pageable pageable);
    List<Contact> findContactsByUserId(int userId);
    Contact getContactById(int id);
    void deleteContact(Contact contact);
    ContactDTO getContactDTOById(int id);
    Contact saveContactDTO(ContactDTO contactDTO);
}
