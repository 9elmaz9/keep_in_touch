package com.example.keep_in_touch.service.impl;

import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

 // @Autowired
 // private UserRepository userRepository;

 // @Autowired
 // private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public User registerUser(User user) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void updatePassword(User user) {

    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User findByEmailAndSecretQuestion(String email, String secretQuestion) {
        return null;
    }

    @Override
    public boolean verifySecretAnswer(User user, String secretAnswer) {
        return false;
    }

    @Override
    public UserDTO getUserDTOById(int id) {
        return null;
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) {
        return null;
    }

    @Override
    public UserDTO registerUserDTO(UserDTO userDTO) {
        return null;
    }

    @Override
    public void updatePassword(UserDTO userDTO) {

    }

    @Override
    public List<UserDTO> getAllUserDTOs() {
        return null;
    }
}
