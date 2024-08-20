package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    //1
    public  String home(Model model,
                        Principal principal){
        return null;
    }

    //2
    public String about(Model model,
                        Principal principal){
        return  null;
    }


    //3
    public String singup(Model model){
        return  null;

    }

    //4

    public String login(Model model,
                        HttpSession session){
        return  null;
    }

    //5

    public String handleRegistration(@Valid @ModelAttribute("user") UserDTO userDTO,
                                     BindingResult result1,
                                     @RequestParam(value = "agreement",
                                     defaultValue = "false") boolean agreement,
                                     Model model,
                                     HttpSession session){
        return null;
    }

    //6
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 HttpSession session){
        return null;
    }
}


