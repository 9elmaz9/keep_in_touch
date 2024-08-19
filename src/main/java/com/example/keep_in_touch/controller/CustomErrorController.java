package com.example.keep_in_touch.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//This controller is responsible for displaying a custom error page when errors occur in the application.
@Controller    //the ErrorController interface, which provides methods to handle error paths
public class CustomErrorController implements ErrorController {

    //This method is mapped to the "/error" endpoint and is triggered when an error occurs.
    //It returns the name of the error view, which will be rendered to the user.
    @GetMapping("/error")
    public String handleError(){
        //Return the view name "error", which Spring will use to render the error page.
        return "error";
    }

}
