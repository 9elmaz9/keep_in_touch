package com.example.keep_in_touch.service.impl;

import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.UserRepository;
import com.example.keep_in_touch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Внедрение кодировщика паролей для шифрования паролей и секретных ответов
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;



    //возвращает пользователя по его имени
    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUserName(username);
    }

    //сохраняет ентити в базеданных
    @Override
    public void saveUser(User user) {
        userRepository.save(user);

    }

    //регистрация нового пользователя и  шифрование его пароля и секретный ответ
    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSecretAnswer(passwordEncoder.encode(user.getSecretAnswer())); // Encrypting the secretAnswer
        return userRepository.save(user);

    }

    // finds a user by email
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //update password обновляет пароль пользователя - шифруя его перед сохранением
    @Override
    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }


    //возвращает паролль по его ид- если находит или пустой
    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }


    //gets all users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //находит пользователя по его емайлу и секретному вопросу
    @Override
    public User findByEmailAndSecretQuestion(String email, String secretQuestion) {
        return userRepository.findByEmailAndSecretQuestion(email,secretQuestion);
    }


    // check the secret answer for a user , return true if correct
    @Override
    public boolean verifySecretAnswer(User user, String secretAnswer) {
        return passwordEncoder.matches(secretAnswer, user.getSecretAnswer());
    }


    //возвращает UserDTO по его ид
    @Override
    public UserDTO getUserDTOById(int id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElse(null);
    }


    //возвращает  UserDTO по его имени пользователя
    @Override
    public UserDTO getUserDTOByUsername(String username) {  //Optional для безопасного преобразования User в UserDTO, если User не равен null
        User user = getUserByUsername(username);
        return user != null ? userMapper.toDTO(user) : null;
        // Логирование, если пользователь не найден
        // logger.warn("User with username " + username + " not found");
    }


    //регистрируем нового пользователя на основе  UserDTO и   шифруте пароль и секретный ответ
    @Override
    public UserDTO registerUserDTO(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSecretAnswer(passwordEncoder.encode(user.getSecretAnswer()));

        // Логирование (если нужно)
        // logger.info("User registered with ID: " + savedUser.getId());

        return userMapper.toDTO(userRepository.save(user));
    }


    //update  the password of a user from a userdto and use encoder before saved
    @Override
    public void updatePassword(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    //возвращает список всез userDto
    @Override
    public List<UserDTO> getAllUserDTOs() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

    }
}
