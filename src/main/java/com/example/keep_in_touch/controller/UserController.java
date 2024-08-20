package com.example.keep_in_touch.controller;


import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.helper.Message;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserMapper userMapper;

    //@Autowired
    //private NotificationService notificationService;

    @ModelAttribute
    //Метод добавляет данные о текущем пользователе в модель, чтобы они были доступны на всех страницах
    public void addCommonData(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", userMapper.toDTO(user));
    }

    @GetMapping("/dashboard")
    //отображает главную страницу пользователя dashboard с инфо о текущем пользователе
    // principal: Предоставляет информацию о текущем аутентифицированном пользователе, в частности, его имя
    public String dashboard(Model model, Principal principal, HttpSession session) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", userMapper.toDTO(user));
        model.addAttribute("title", "User Dashboard");
        return "normal/user_dashboard";
    }


    //add-contact-form: Отображает форму для добавления нового контакта
    @GetMapping("/add-contact-form")
    public String addContactForm(Model model, Principal principal, HttpSession session) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("title", " Add contact");
        model.addAttribute("contact", new Contact());
        return "/normal/add_contact_form";
    }


    //process-contact: Обрабатывает форму добавления контакта. Загружает изображение профиля, сохраняет контакт,связывая его с текущим пользователем.
    @PostMapping("/process-contact")
    public String processContactForm(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
                                     Principal principal, HttpSession session, Model model) {
        try {
            // Получаем имя текущего аутентифицированного пользователя
            String username = principal.getName();
            // Находим пользователя в базе данных по имени
            User user = userService.getUserByUsername(username);

            // Проверяем загруженное изображение
            if (file.isEmpty()) {
                // Если файл не загружен, устанавливаем изображение по умолчанию
                contact.setImage("default.webp");
            } else if (!file.getContentType().equals("image/jpeg") &&
                    !file.getContentType().equals("image/png") &&
                    !file.getContentType().equals("image/jpg")) {
                // Если формат файла не поддерживается, устанавливаем изображение по умолчанию
                contact.setImage("default.webp");
            } else {
                // Устанавливаем имя загруженного изображения и сохраняем его на сервере
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            // Устанавливаем текущего пользователя для контакта и сохраняем контакт в базе данных
            contact.setUser(user);
            contactService.saveContact(contact);

            // Очищаем форму и добавляем сообщение об успешном добавлении контакта
            model.addAttribute("contact", new Contact());
            session.setAttribute("message", new Message("Contact added successfully ...", "alert-success"));
        } catch (Exception e) {
            // В случае ошибки сохраняем контакт и добавляем сообщение об ошибке
            model.addAttribute("contact", contact);
            session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
        }

        // Возвращаем представление для отображения формы добавления контакта
        return "normal/add_contact_form";
    }

    //Methods for displaying and managing contacts


    //Отображает список контактов пользователя с поддержкой пагинации.
    public String showContactHandler(@PathVariable("page") Integer page, Model model, Principal principal, HttpSession session) {
        return null;
    }


    //Отображает подробную информацию о контакте
    public String handleContactDetail(@PathVariable("cId") Integer cId, Model model, Principal principal, HttpSession session) {
        return null;
    }


    // Удаляет контакт, если текущий пользователь является его владельцем
    public String deleteContactHandler(@PathVariable("cId") Integer cId, Principal principal, HttpSession session) {
        return null;
    }


    //Отображает форму для обновления контакта
    public String updateFormHandler(@PathVariable("cId") Integer cId, Model model) {
        return null;
    }


    // Обрабатывает форму обновления контакта, включая обновление изображения профиля
    public String processUpdateContactHandler(@ModelAttribute("contact") Contact contact,
                                              @RequestParam("profileImage") MultipartFile file, Model model, Principal principal,
                                              HttpSession session) {
        return null;
    }


    //profile: Отображает страницу профиля пользователя
    public String profileHandler(Model model, Principal principal, HttpSession session) {
        return null;
    }


    //Отображает форму для редактирования профиля
    public String updateProfileHandler(Model model) {
        return null;
    }


    //Обрабатывает форму редактирования профиля, включая обновление имени, информации "О себе" и изображения профиля.
    public String processEditProfileForm(@RequestParam("name") String name, @RequestParam("about") String about,
                                         @RequestParam("profileImage") MultipartFile file, Principal principal,
                                         HttpSession session) throws IOException {
        return null;
    }


    //Методы для управления паролем


    //Отображает страницу настроек пользователя
    public String openSettings(Model model, Principal principal, HttpSession session) {
        return null;
    }


    //Обрабатывает изменение пароля пользователя. Если старый пароль совпадает с текущим, новый пароль шифруется и сохраняется.
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
        return null;
    }




    //Методы для восстановления пароля

    //Отображает форму для восстановления пароля
    public String showForgotPasswordForm(){return  null;}


    //Обрабатывает ввод email для восстановления пароля, отображает секретный вопрос
    public String processForgotPasswordForm(@RequestParam("email") String email, Model model){ return  null;}


    //Проверяет ответ на секретный вопрос. Если ответ правильный, отображается форма для сброса пароля
    public  String verifySecretQuestion(@RequestParam("email")String  email,
                                        @RequestParam("secretAnswer") String secretAnswer , Model model){
        return  null;
    }


    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password , Model model){return null;}
}
