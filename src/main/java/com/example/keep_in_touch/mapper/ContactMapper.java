package com.example.keep_in_touch.mapper;

import com.example.keep_in_touch.dto.ContactDTO;
import com.example.keep_in_touch.entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/* Mapper class for converting between Contact entities and ContactDTO objects.
 *Этот класс служит для преобразования данных между сущностями Contact и объектами ContactDTO.
 *Использование маппера помогает отделить слои приложения и упрощает тестирование и поддержку кода.*/
@Component
public class ContactMapper {

    @Autowired
    private UserMapper userMapper; //Внедрение маппера для User, если понадобится обработка связанного пользователя


    public ContactDTO toDTO(Contact contact){
        ContactDTO dto=new ContactDTO();
        dto.setCid(contact.getCid());
        dto.setName(contact.getName());
        dto.setNickName(contact.getNickName());
        dto.setWork(contact.getWork());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());
        dto.setImage(contact.getImage());
        dto.setDescription(contact.getDescription());
        dto.setUserId(contact.getUser().getId());

        return dto;

    }

    public Contact toEntity(ContactDTO dto){
        Contact contact = new Contact();
        contact.setCid(dto.getCid());
        contact.setName(dto.getName());
        contact.setNickName(dto.getNickName());
        contact.setWork(dto.getWork());
        contact.setEmail(dto.getEmail());
        contact.setPhone(dto.getPhone());
        contact.setImage(dto.getImage());
        contact.setDescription(dto.getDescription());

        return contact;


    }
}
