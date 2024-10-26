package com.example.keep_in_touch.service;

import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.UserRepository;
import com.example.keep_in_touch.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


// тестирования сервиса UserServiceImpl, который отвечает за работу с пользователями в вашем приложении
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    // проверяет, правильно ли сервис возвращает пользователя по имени пользователя
    @Test
    public  void testGetUserByUsername(){
        User user=new User();
        user.setUsername("admin");
        when(userRepository.getUserByUserName("admin")).thenReturn(user); //вызов() с аргументов админ - возвразает user

        //вызываем метод сервиса и результат сравнивается с ожидаемым именем пользователя
        User result = userService.getUserByUsername("admin");
        assertEquals("admin",result.getUsername());

    }



    // проверяет, правильно ли происходит регистрация нового пользователя, включая шифрование пароля и секретного ответа
    @Test
    public void testRegisterUser(){
        User user= new User();
        user.setPassword("plainPassword");
        user.setSecretAnswer("plainAnswer");

        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(passwordEncoder.encode("plainAnswer")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);


        // ()пароль и секретный ответ были зашифрованы корректно
        User result =userService.registerUser(user);
        assertEquals("encodedPassword",result.getPassword());
        assertEquals("encodedPassword",result.getSecretAnswer());

    }

    //правильно ли сервис находит пользователя по электронной почте
    @Test
    public  void testFindByEmail(){
        User user= new User();
        user.setEmail("admin@example.com");

        when(userRepository.findByEmail("admin@example.com")).thenReturn(user);

        //электронная почта совпадает с ожидаемой
        User result = userService.findByEmail("admin@example.com");
        assertEquals("admin@example.com", result.getEmail());

    }


    // правильно ли обновляется пароль пользователя
    @Test
    public void testUpdatePassword(){

        User user= new User();
        user.setPassword("testPassword");

        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");

        userService.updatePassword(user);
        verify(userRepository, times(1)).save(user); //  проверяется, что метод save был вызван на userRepository один раз
        assertEquals("encodedPassword", user.getPassword());
    }


    // правильно ли сервис находит пользователя по его ID
    @Test
    public void testGetUserId(){
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user)); //  возвращает объект user в обертке Optional

        Optional<User> result = userService.getUserById(1);
        assertTrue(result.isPresent());
        assertEquals(1,result.get().getId());
    }

    @Test
    public void testVerifySecretAnswer(){

        User user= new User();
        user.setSecretAnswer("encodedAnswer");

        when(passwordEncoder.matches("testAnswer", "encodedAnswer")).thenReturn(true);

        boolean result = userService.verifySecretAnswer(user, "testAnswer");
        assertTrue(result);
    }
}