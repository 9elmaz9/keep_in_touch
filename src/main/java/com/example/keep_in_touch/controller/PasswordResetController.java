package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.helper.Message;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

//manage password reset operations
@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    //shows the forgot password form
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }


    //handles the forgotten password form
    @PostMapping("/forgot-password")
    public String processForgotPasswordForm(@RequestParam("email") String email,
                                            Model model) {
        //get user by email
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No account associated with that email was found.");
            return "forgot_password_form";
        }
        //передаем данные секретнвый вопрос  .мапер преобразует юзер в дто
        UserDTO userDTO = userMapper.toDTO(user);
        model.addAttribute("email", email); // for newt step
        model.addAttribute("secretQuestion", userDTO.getSecretQuestion());

        return "verify_secret_question";
    }


    //Validatrs the answer to the security question ->check if the   answer  is correct
    @PostMapping("/verify-secret-question")
    public String verifySecretQuestion(@RequestParam("email") String email,
                                       @RequestParam("secretAnswer") String secretAnswer,
                                       Model model) {
        User user = userService.findByEmail(email);
        // if nor- error
        if (user == null || !passwordEncoder.matches(secretAnswer, user.getSecretAnswer())) {
            //if not ->
            model.addAttribute("error", "Incorrect answer to the security question");
            model.addAttribute("email", email);
            model.addAttribute("secretQuestion", user.getSecretQuestion());
            return "verify_secret_question";

        }
        //if - ok -> update password form next в форму
        model.addAttribute("email", email);
        return "reset_password_form"; // reset password
    }


    //resrt the password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                Model model,
                                HttpSession session) {
        //check new password , doest it meet the requirements
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,}$";
        if (!Pattern.matches(passwordPattern, password)) {
            model.addAttribute("error", "The password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.");
            model.addAttribute("email", email);
            return "reset_password_form";
        }

        //check is user exist by email
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No account associated with that email address was found.");
            return "forgot_password_form";
        }

        //save new encrypted password
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);
        //succssed message
        session.setAttribute("message", new Message("Password successfully updated", "alert-success"));


        return "login";
    }
}
