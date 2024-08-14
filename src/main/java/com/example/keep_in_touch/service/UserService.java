package com.example.keep_in_touch.service;

import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getUserByUsername(String username);
    void saveUser(User user);
    User registerUser(User user);
    User findByEmail(String email);
    void updatePassword(User user);
    Optional<User> getUserById(int id);
    List<User> getAllUsers();
    User findByEmailAndSecretQuestion(String email, String secretQuestion);
    boolean verifySecretAnswer(User user, String secretAnswer);

    UserDTO getUserDTOById(int id);
    UserDTO getUserDTOByUsername(String username);
    UserDTO registerUserDTO(UserDTO userDTO);
    void updatePassword(UserDTO userDTO);
    List<UserDTO> getAllUserDTOs();

}
