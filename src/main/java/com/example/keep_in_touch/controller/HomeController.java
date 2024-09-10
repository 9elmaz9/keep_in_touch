package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.helper.Message;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;


    //Show the home page
    @GetMapping("/")
    public String home(Model model,
                       Principal principal) {

        if (principal == null) {
            model.addAttribute("user", null);
            return "home";
        }
        //get the username
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        model.addAttribute("user", userMapper.toDTO(user)); // add the users info to the model
        model.addAttribute("title", "Home  - K I T");

        return "home";
    }

//contact
    @GetMapping("/about")
    public String about(Model model,
                        Principal principal) {
        if (principal == null) {
            model.addAttribute("user", null);
            return "about";
        }
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        model.addAttribute("user", userMapper.toDTO(user));
        model.addAttribute("title", " About - K I T");

        return "about";
    }


    // траница регитрации = пустой обьект
    @GetMapping("/signup")
    public String singup(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("title", "Register - K I T");
        // clears any previous messages
        model.addAttribute("message", null);
        return "signup";

    }

    //Метод отображает страницу входа, добавляет флаг для указания, что это страница входа,
    @GetMapping("/signin")
    public String login(Model model,
                        HttpSession session) {
        //aAdds a flag to indicate it's the login page
        model.addAttribute("userLogin", true);
        model.addAttribute("title", " Login - KEEP IN TOUCH");

        //если в сессии есть валидация выводится в консоль
        if (session.getAttribute("validation") != null) {
            String validationStatus = (String) session.getAttribute("validation");
            System.out.println(validationStatus);
        }
        return "login";

    }


    //Processes user registration . валидацтя агримент и кодировка пароля
    @PostMapping("/do-register")
    public String handleRegistration(@Valid @ModelAttribute("user") UserDTO userDTO,
                                     BindingResult result1,
                                     @RequestParam(value = "agreement",
                                             defaultValue = "false") boolean agreement,
                                     Model model,
                                     HttpSession session) {
        try {
            if (!agreement) {
                throw new Exception("You have not accepted the terms and conditions!");
            }
            if (result1.hasErrors()) {
                model.addAttribute("user", userDTO);
                return "signup";
            }

            //map the dto to the user entity and sert default values
            User user = userMapper.toEntity(userDTO);
            user.setEnabled(true);
            user.setRole("ROLE_USER");
            user.setImageUrl("default.webp");
            user.setPassword(passwordEncoder.encode(user.getPassword())); //encrypt the password
            user.setSecretAnswer(passwordEncoder.encode(user.getSecretAnswer()));// encrypt the secret answer
            user.setValidated(1);

            //save yhe user to db
            User result = userRepository.save(user);
            model.addAttribute("user", new UserDTO());
            model.addAttribute("message", new Message("Registration completed successfully ", "alert-primary"));

        } catch (Exception e) {
            // any errors that come during registration
            model.addAttribute("user", userDTO);
            model.addAttribute("message", new Message("An error occurred" + e.getMessage(), "alert-danger"));
        }

        return "signup";
    }


    //Processes password change request
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 HttpSession session) {

       // текузей сессии
        String email = (String) session.getAttribute("email");

        User user = this.userRepository.getUserByUserName(email);
        //encrypt новый парольи и устанавливается
        user.setPassword(this.passwordEncoder.encode(newPassword));


        this.userRepository.save(user);

        session.setAttribute("message", new Message("Password changed successfully", "alert-success"));
        return "login";
    }
}


