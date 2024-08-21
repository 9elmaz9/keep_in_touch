package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.ContactRepository;
import com.example.keep_in_touch.repository.UserRepository;
import com.example.keep_in_touch.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.stream.Collectors;

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
    /** Shows the admin dashboard with a list of users
     Handles showing the admin dashboard for a specific page */
    @GetMapping("/dashboard/{page}")
    public String getDashboard(@PathVariable("page") Integer page,
                               Principal principal,
                               Model model){
        //get the loggedin admins username
        String userName = principal.getName();
        //find the admin user by email
        User adminUser =this.userRepository.findByEmail(userName);

        Pageable pageable = PageRequest.of(page,9);
        Page<User> users =this.userRepository.findAll(pageable);


        model.addAttribute("user",userMapper.toDTO(adminUser)); // admin info to the model
        model.addAttribute("users",users.getContent().stream().map(userMapper::toDTO).collect(Collectors.toList())); // add the list of users to the model


        // Add title, current page, and total pages to the model
        model.addAttribute("title","Admin Dashboard");
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",users.getTotalPages());

        return  "admin/admin_dashboard";// show admin dashboard
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
