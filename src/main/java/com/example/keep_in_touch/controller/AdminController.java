package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.ContactRepository;
import com.example.keep_in_touch.repository.UserRepository;
import com.example.keep_in_touch.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

// Controller class for managing admin-related operations.
//This controller provides methods for viewing users, user profiles, and deleting users.
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    //1
    public String getDashboard(@PathVariable("page") Integer page,
                               Principal principal,
                               Model model){
        return  null;
    }

    //2
    public String getUserProfile(@PathVariable("uid") Integer uid,
                                 Model model){
        return null;
    }

    //3
    public String deleteUserHandler(@PathVariable("uid") Integer uid,
                                    HttpSession session,
                                    Principal principal){
        return null;
    }

    //4
    public String adminDashboard(Model model,
                                 @RequestParam(defaultValue = "0") int page){
        return null;
    }

}
