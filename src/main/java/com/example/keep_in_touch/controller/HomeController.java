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

    //1
    //Show the home page
    @GetMapping("/")
    public  String home(Model model,
                        Principal principal) {

        //if not logged in user then ->
        if (principal == null){
            model.addAttribute("user", null);
        return "home";
    }
        //get the username
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName); // get data from db
        model.addAttribute("usser", userMapper.toDTO(user)); // add the users info to the model
        model.addAttribute("title" , "Home  - KEEP IN TOUCH");
        return "home";
    }


    //2
    //Shows the "About" page. If the user is logged in, adds their information to the page.
    @GetMapping("/about")
    public String about(Model model,
                        Principal principal){
        //if the user isnt logged in ->
        if(principal == null) {
            model.addAttribute("user", null);
            return "about";
        }
        //if yes
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName); // retrive to db
        model.addAttribute("user", userMapper.toDTO(user)); // add user information to the model
        model.addAttribute("title", " About - KEEP IN TOUCH");

        return  "about";
    }


    //3
    // Shows the registration page where users can sign up.
    @GetMapping("/signup")
    public String singup(Model model){
        // adds an empty user form to the page
        model.addAttribute("user" ,new UserDTO());
        //sets the page title
        model.addAttribute("title", "Register - KEEP IN TOUCH");
        // clears any previous messages
        model.addAttribute("message" , null);
        return  "singup";

    }

    //4 Shows the login page
    @GetMapping("/signin")
    public String login(Model model,
                        HttpSession session){
        //aAdds a flag to indicate it's the login page - state attribute
        model.addAttribute("userLogin" , true);
        //sets the page title
        model.addAttribute("title" , " Login - KEEP IN TOUCH");

        //check if theres any validation message in the sseddipn and print
        if(session.getAttribute("validation") != null){
            String validationStatus = (String) session.getAttribute("validation");
            System.out.println(validationStatus); //logs the validation status to the console
        }
        return  "login"; // page

        //Метод отображает страницу входа, добавляет флаг для указания, что это страница входа,
        //устанавливает заголовок страницы и проверяет наличие сообщения валидации в сессии,
        //выводя его в консоль, если оно существует. Затем возвращается представление страницы входа.
    }

    //5
    //Processes user registration
    @PostMapping("/do-register")
    public String handleRegistration(@Valid @ModelAttribute("user") UserDTO userDTO,
                                     BindingResult result1,
                                     @RequestParam(value = "agreement",
                                     defaultValue = "false") boolean agreement,
                                     Model model,
                                     HttpSession session){
        try {
            //check if the user agreed to the terms and conditions
            //Проверка согласия пользователя с условиями использования.
            if( !agreement){
                throw new Exception("You have not acepted the terms and conditions!");
            }
            if(result1.hasErrors()){
                model.addAttribute("user", userDTO);
                return "signup";
            }

            //map the dto to the user entity and sert default values
            User user = userMapper.toEntity(userDTO);
            user.setEnabled(true); // enable the user
            user.setRole("ROLE_USER");// set the role
            user.setImageUrl("default.webp"); // set a def profile image
            user.setPassword(passwordEncoder.encode(user.getPassword())); //encrypt the password
            user.setSecretAnswer(passwordEncoder.encode(user.getSecretAnswer()));// encrypt the secret answer

            //save yhe user to db
            User result = userRepository.save(user);
            model.addAttribute("user", new UserDTO());
            model.addAttribute("message" , new Message("Registration completed successfully " , "alert-primary"));

            }catch (Exception e){
            //handle any errors that come during registration
            model.addAttribute("user" , userDTO);
            model.addAttribute("message" , new Message("An error occured" +e.getMessage(),"alert-danger"));
        }


        return "singup"; // page
    }

    //6
    //Processes password change request.
    @PostMapping("/change-password") //отправке формы
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 HttpSession session){

        //get the users email from the session
        String email =(String) session.getAttribute("email");

        //find user by email in db
        User user = this.userRepository.getUserByUserName(email);
        //encrypt the new password and set it for the user
        user.setPassword(this.passwordEncoder.encode(newPassword));


        // Save the updated user in the database.
        this.userRepository.save(user);

        // Add a success message to the session.
        session.setAttribute("message" , new Message("Password changed successfylly" , "alert-success"));
        //redirect to the login page
        return "login";
    }
}


