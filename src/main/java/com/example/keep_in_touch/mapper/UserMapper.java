package com.example.keep_in_touch.mapper;


import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;
import org.springframework.stereotype.Component;


//Mapper class for converting between User entities and UserDTO objects.

@Component // будет управляться контейнером Spring.
public class UserMapper {

    // Converts a User entity to a UserDTO object
    //не раскрывая внутреннюю структуру entity
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();

        mapCommonFields(user, dto); // загрузка инфы из ю в д

        dto.setValidated(user.getValidated() != null ? user.getValidated() : 0);
        return dto;
    }


    //из UserDTO в сущность User
    public User toEntity(UserDTO dto) {
        User user = new User();

        mapCommonField(dto, user);
        user.setValidated(dto.getValidated());

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
        dto.setSecretAnswer(user.getSecretAnswer());

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
        user.setSecretAnswer(dto.getSecretAnswer());

    }

}
