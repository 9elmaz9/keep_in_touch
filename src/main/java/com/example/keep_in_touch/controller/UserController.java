package com.example.keep_in_touch.controller;


import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    //@Autowired
    //private NotificationService notificationService;

@ModelAttribute   //Метод добавляет данные о текущем пользователе в модель, чтобы они были доступны на всех страницах
    public void addCommonData(Model model, Principal principal){
    String username = principal.getName();
    User user = userService.getUserByUsername(username);
    model.addAttribute("user", userMapper.toDTO(user));
}



}
