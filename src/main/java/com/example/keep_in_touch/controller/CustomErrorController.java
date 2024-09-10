package com.example.keep_in_touch.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class
CustomErrorController implements ErrorController { // interface, which provides methods to handle error paths


     //is triggered when an error occurs
    // It returns the name of the error view, which will be rendered to the user

    @GetMapping("/error")
    public String handleError() {
        return "error";
    }

}
