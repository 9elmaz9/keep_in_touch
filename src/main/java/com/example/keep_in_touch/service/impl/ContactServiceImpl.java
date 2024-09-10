package com.example.keep_in_touch.service.impl;

import com.example.keep_in_touch.dto.ContactDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.Event;
import com.example.keep_in_touch.mapper.ContactMapper;
import com.example.keep_in_touch.repository.ContactRepository;
import com.example.keep_in_touch.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

//manage contact entities ,
//Реализация ContactServiceImpl использует репозиторий и маппер для выполнения операций, определенных в интерфейсе
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactMapper contactMapper;


    /*This method maintains contact. If the cid (contact ID) is not zero, it updates the existing contact; otherwise,
    it creates a new one.For existing contacts, associated events are preserved to prevent overwriting or deletion*/
    @Override
    public void saveContact(Contact contact) {
        if (contact.getCid() != 0) {
            Contact existingContact = contactRepository.findById(contact.getCid()).orElse(null);
            if (existingContact != null) {
                existingContact.setName(contact.getName());
                existingContact.setNickName(contact.getNickName());
                existingContact.setWork(contact.getWork());
                existingContact.setEmail(contact.getEmail());
                existingContact.setPhone(contact.getPhone());
                existingContact.setImage(contact.getImage());
                existingContact.setDescription(contact.getDescription());

                // Preserve existing events without clearing them
                Set<Event> existingEvents = existingContact.getEvents();
                contact.setEvents(existingEvents);

                contact = existingContact;
            }
        }
            contactRepository.save(contact);

    }


    //Находит страницу контактов для конкретного пользователя с поддержкой пагинации
    @Override
    public Page<Contact> findContactsByUserId(int userId, Pageable pageable) {
        return contactRepository.findContactsByUserId(userId, pageable);
    }


    //Находит список всех контактов для конкретного пользователя
    @Override
    public List<Contact> findContactsByUserId(int userId) {
        return contactRepository.findContactsByUserId(userId);
    }


    //возвращает контакт по его ид
    @Override
    public Contact getContactById(int id) {
        return contactRepository.findById(id).orElse(null);
    }


    //delet a contact from database
    @Override
    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    //возвращает контатДто по его ид
    @Override
    public ContactDTO getContactDTOById(int id) {
        Contact contact = getContactById(id);
        return contact != null ? contactMapper.toDTO(contact) : null;
    }


    //Сохраняет ContactDTO, преобразует его в Contact, и возвращает сохраненную сущность
    @Override
    public Contact saveContactDTO(ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        return contactRepository.save(contact);
        //Сохраняет ContactDTO, преобразует его в Contact, и возвращает сохраненную сущность.
    }
}
