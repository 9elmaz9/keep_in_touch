package com.example.keep_in_touch.service;

import com.example.keep_in_touch.dto.ContactDTO;
import com.example.keep_in_touch.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    //Сохраняет сущность Contact в базе данных
    void saveContact(Contact contact);

    //Находит страницу контактов для конкретного пользователя с поддержкой пагинации
    Page<Contact> findContactsByUserId(int userId, Pageable pageable);

    //Находит список всех контактов для конкретного пользователя.
    List<Contact> findContactsByUserId(int userId);

    //Возвращает контакт по его идентификатору
    Contact getContactById(int id);

    //Удаляет сущность Contact из базы данных
    void deleteContact(Contact contact);

    //Возвращает ContactDTO по его идентификатору
    ContactDTO getContactDTOById(int id);

    //Сохраняет ContactDTO, преобразует его в Contact и возвращает сохраненную сущность
    Contact saveContactDTO(ContactDTO contactDTO);
}
