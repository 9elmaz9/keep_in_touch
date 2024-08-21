package com.example.keep_in_touch.mapper;


import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;
import org.springframework.stereotype.Component;


//Mapper class for converting between User entities and UserDTO objects.

@Component // будет управляться контейнером Spring.
public class UserMapper {

    // Converts a User entity to a UserDTO object
    //Преобразует сущность User в объект UserDTO,не раскрывая внутреннюю структуру entity
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        // Call method to map common fields/Вызов метода для сопоставления общих полей
        mapCommonFields(user, dto);
        // Set the value to validated, check for null
        // Установка значения validated, проверка на null - чтобы избежать возможных ошибок при преобразовании данных.
        dto.setValidated(user.getValidated() != null ? user.getValidated() : 0);
        return dto;
    }


    //Converts a UserDTO object to a User entity/Преобразует объект UserDTO в сущность User
    public User toEntity(UserDTO dto) {
        User user = new User();
        mapCommonField(dto, user); // уже все ок !
        user.setValidated(dto.getValidated());
        // Логирование
        // logger.info("Converted UserDTO to User with validated status: " + user.getValidated());
        return user;
    }

    //Маппинг общих полей из сущности User в объект UserDTO
    //Mapping common fields from User entity to UserDTO object
    private void mapCommonFields(User user, UserDTO dto) {
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());
        dto.setImageUrl(user.getImageUrl());
        dto.setAbout(user.getAbout());
        dto.setSecretQuestion(user.getSecretQuestion());
        dto.setSecretQuestion(user.getSecretQuestion());
    }


    //Маппинг общих полей из объекта UserDTO в сущность User
    //Mapping common fields from UserDTO object  to User entity
    private void mapCommonField(UserDTO dto, User user) {
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setEnabled(dto.isEnabled());
        user.setImageUrl(dto.getImageUrl());
        user.setAbout(dto.getAbout());
        user.setSecretQuestion(dto.getSecretQuestion());
        user.setSecretQuestion(dto.getSecretQuestion());


    }

}
