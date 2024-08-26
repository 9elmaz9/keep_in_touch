package com.example.keep_in_touch.helper;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


//Helper class for managing session attributes
// SessionHelper создан для управления сессиями в вашем веб-приложении

@Component  //Указывает, что этот класс является Spring Bean
public class SessionHelper {

    //Удаляет атрибут "message" из текущей HTTP-сессии
    public void removeMessageFromSession() {
        try {
            // Получает текущую сессию и удаляет атрибут "message"
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            System.out.println(e.getMessage()); // если ошибка то = смс
        }
    }


    // Удаляет атрибут "validation" из текущей HTTP-сессии
    public void removeValidationFromSession() {
        try {
            // Получает текущую сессию и удаляет атрибут "validation"
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("validation");
        } catch (Exception e) {
            System.out.println(e.getMessage()); // если ошибка то = смс
        }
    }


}
