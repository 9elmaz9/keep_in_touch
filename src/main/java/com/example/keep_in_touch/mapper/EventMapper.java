package com.example.keep_in_touch.mapper;


import com.example.keep_in_touch.dto.EventDTO;
import com.example.keep_in_touch.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Event entities and EventDTO objects.
 * Этот класс служит для преобразования данных между сущностями Event и объектами EventDTO.
 * Использование маппера помогает отделить слои приложения и упрощает тестирование и поддержку кода
 */

@Component
public class EventMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContactMapper contactMapper;


    //Converts an Event entity to an EventDTO object.
    //Преобразует сущность Event в объект EventDTO.
    public EventDTO toDTO(Event event){
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setStart(event.getStart());
        dto.setNote(event.getNote());
        dto.setUserId(event.getUser().getId());
        dto.setContactId(event.getContact().getCid());
        dto.setContactName(event.getContact().getName());  // Добавлено имя контакта в DTO

        return dto;
    }


   // Converts an EventDTO object to an Event entity
   //Преобразует объект EventDTO в сущность Event.
    public Event toEntity(EventDTO dto){

        Event event= new Event();
        event.setId(dto.getId());
        event.setTitle(dto.getTitle());
        event.setStart(dto.getStart());
        event.setNote(dto.getNote());

        //User и Contact не устанавливаются, так как их нужно устанавливать отдельно
        //после загрузки соответствующих сущностей из базы данных.
        return event;
    }
}
