package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
//manage password reset operations
@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;




    //1
    public String showForgotPasswordForm(){
        return null;
    }

    //2
    public String processForgotPasswordForm(@RequestParam("email") String email ,
                                            Model model){
        return null;
    }

    //3

    public String verifySecretQuestion(@RequestParam("email") String email,
                                       @RequestParam("secretAnswer") String secretAnswer,
                                       Model model){
        return null;
    }

    //4
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                Model model,
                                HttpSession session){
        return null;
    }
}
