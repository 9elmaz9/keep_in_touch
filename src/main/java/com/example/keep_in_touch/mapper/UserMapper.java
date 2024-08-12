package com.example.keep_in_touch.mapper;


import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;
import org.springframework.stereotype.Component;

/** Mapper class for converting between User entities and UserDTO objects.
 */
@Component // будет управляться контейнером Spring.
public class UserMapper {

    // Converts a User entity to a UserDTO object
    //Преобразует сущность User в объект UserDTO,не раскрывая внутреннюю структуру entity.
    public UserDTO toDto(User user){
        UserDTO dto = new UserDTO();
        // Call method to map common fields/Вызов метода для сопоставления общих полей
        mapCommonFields(user,dto);
        // Set the value to validated, check for null/ Установка значения validated, проверка на null - чтобы избежать возможных ошибок при преобразовании данных.
        dto.setValidated(user.getValidated() != null ? user.getValidated() : 0);
    return dto;
    }


    //  Converts a UserDTO object to a User entity/Преобразует объект UserDTO в сущность User
    public User toEntity(UserDTO dto){

    }

    private void mapCommonFields(User user, UserDTO dto){}

    private void mapCommonField(UserDTO dto, User user){}


}
