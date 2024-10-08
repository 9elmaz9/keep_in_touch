package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.helper.Message;
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
import java.util.List;
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


    // Shows the admin dashboard with a list of users,
    @GetMapping("/dashboard/{page}")
    public String getDashboard(@PathVariable("page") Integer page,
                               Principal principal,
                               Model model) {
        String userName = principal.getName();
        User adminUser = this.userRepository.findByEmail(userName);

        Pageable pageable = PageRequest.of(page, 9);
        Page<User> users = this.userRepository.findAll(pageable);


        model.addAttribute("user", userMapper.toDTO(adminUser)); // admin users info to the model
        model.addAttribute("users", users.getContent().stream().map(userMapper::toDTO).collect(Collectors.toList())); // add the list of users to the model


        model.addAttribute("title", "Admin Dashboard");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());

        return "admin/admin_dashboard";
    }


    // Shows the profile page for a specific user.Handles showing the profile page for a specific
    // user отображение профиля польователя по айди .если  нет то ощибка
    @GetMapping("/user-profile/{uid}")
    public String getUserProfile(@PathVariable("uid") Integer uid,
                                 Model model) {

        User user = this.userRepository.findById(uid).orElse(null);
        model.addAttribute("title", "User Profile");
        model.addAttribute("user", userMapper.toDTO(user));
        return "admin/user_profile";
    }


    // Deletes a user by their ID
    @GetMapping("user-delete/{uid}")
    public String deleteUserHandler(@PathVariable("uid") Integer uid,
                                    HttpSession session,
                                    Principal principal) {
        String adminUsername = principal.getName();
        User adminUser = userService.getUserByUsername(adminUsername);

        if (!"ROLE_ADMIN".equals(adminUser.getRole())) {

            session.setAttribute("message", new Message("You do not have permission to perform this action", "alert-danger"));
            return "redirect:/admin/dashboard/0";
        }


        //find the user to be deleted by id
        User deletedUser = this.userRepository.findById(uid).orElse(null);

        //delete all contact associated with this user
        if (deletedUser != null) {
            List<Contact> contacts = this.contactRepository.findByUser(deletedUser);
            for (Contact contact : contacts) {
                this.contactRepository.delete(contact);
            }

            this.userRepository.delete(deletedUser);
            session.setAttribute("message", new Message("The user was  successfully deleted.", "alert-success"));
        } else {
            //if user not found then ->
            session.setAttribute("message", new Message("The user not found.", "alert-danger"));
        }
        return "redirect:/admin/dashboard/0";
    }


    //Shows the admin dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model,
                                 @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 9);
        Page<User> userPage = userRepository.findAll(pageable);

        //add user list to the model
        model.addAttribute("users", userPage.getContent().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
        //add the curent page number and total pages to the model
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());


        return "admin_dashboard"; // admin/admin_dashboard
    }

}
