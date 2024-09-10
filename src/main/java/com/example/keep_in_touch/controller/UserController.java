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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    //@Autowired
    //private NotificationService notificationService;


    //Метод добавляет данные о текущем пользователе в модель, чтобы они были доступны на всех страницах
    @ModelAttribute
    public void addCommonData(Model model,
                              Principal principal) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", userMapper.toDTO(user));
    }


    //отображение пользовательской панели управления
    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            Principal principal,
                            HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", userMapper.toDTO(user));
        model.addAttribute("title", "User Dashboard");
        return "prof/user_dashboard";
    }


    // Отображает форму для добавления нового контакта
    @GetMapping("/add-contact-form")
    public String addContactForm(Model model,
                                 Principal principal,
                                 HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username); //bd
        model.addAttribute("title", "Add Contact"); // emptyform
        model.addAttribute("contact", new Contact());
        return "prof/add_contact_form";
    }


    //Обрабатывает форму добавления контакта.Загружает изображение профиля, сохраняет контакт,связывая его с текущим пользователем.
    @PostMapping("/process-contact")
    public String processContactForm(@ModelAttribute Contact contact,  // принимает арг
                                     @RequestParam("profileImage") MultipartFile file,
                                     Principal principal,
                                     HttpSession session,
                                     Model model) {

        try {
            String username = principal.getName();
            User user = userService.getUserByUsername(username);

            if (file.isEmpty()) {
                contact.setImage("default.webp");
            } else if (!file.getContentType().equals("image/jpeg") &&
                    !file.getContentType().equals("image/png") &&
                    !file.getContentType().equals("image/jpg")) {
                // then ->
                contact.setImage("default.webp");
            } else {
                //  если ок сохраняем его на сервере
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            // контакт привязан к пользоваелю  после сборки всей инфы о нем = маил телефоне
            contact.setUser(user);
            contactService.saveContact(contact); // bd

            model.addAttribute("contact", new Contact()); // пустое поле новое
            session.setAttribute("message", new Message("Contact added successfully ", "alert-success"));
        } catch (Exception e) {
            // В случае ошибки сохраняем контакт и добавляем сообщение об ошибке
            model.addAttribute("contact", contact);
            session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
        }

        return "prof/add_contact_form";
    }


    //Methods for displaying and managing contacts
    //Отображает  все контакты конкретного пользователя по4
    @GetMapping("/show-contacts/{page}")
    public String showContactHandler(@PathVariable("page") Integer page,
                                     Model model,
                                     Principal principal,
                                     HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        Pageable pageable = PageRequest.of(page, 4);
        // Get the user's contacts for the current page
        Page<Contact> contacts = contactService.findContactsByUserId(user.getId(), pageable);


        // Add the contacts and other info to the model so the view can use it
        model.addAttribute("contacts", contacts);
        model.addAttribute("title", "All Contacts");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contacts.getTotalPages());

        return "prof/show_contacts";

    }


    //Shows the details of a specific contact-получение текущего полтзователя  id ,Отображает подробную информацию о контакте
    @GetMapping("/{cId}/contact")
    public String handleContactDetail(@PathVariable("cId") Integer cId,
                                      Model model,
                                      Principal principal,
                                      HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        //Find the contact by its id
        Contact contact = contactService.getContactById(cId);

        //check if the contact belongs to the log-in usr
        if (user.getId() == contact.getUser().getId()) {
            //add contact to the model so the view can use it
            model.addAttribute("contact", contact);
        }


        return "prof/contact_details";
    }


    //удаление по id
    @GetMapping("/delete/{cId}")
    public String deleteContactHandler(@PathVariable("cId") Integer cId,
                                       Principal principal,
                                       HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        Contact contact = contactService.getContactById(cId);

        if (user.getId() == contact.getUser().getId()) {
            contactService.deleteContact(contact);

            session.setAttribute("message", new Message("Contact deleted successfully!", "alert-success"));
        } else {
            //if not
            session.setAttribute("message", new Message("Permission rejected!", "alert-danger"));
        }

        return "redirect:/user/show-contacts/0";
    }


    //агружает текущие данные для редакции This method displays the form to update a contact
    @PostMapping("/update-contact/{cId}")
    public String updateFormHandler(@PathVariable("cId") Integer cId,
                                    Model model) {

        //страница формы = ниже контакт
        model.addAttribute("title", " Update Contact");
        Contact contact = contactService.getContactById(cId);

        model.addAttribute("contact", contact);

        return "prof/update_contact_form";
    }


    //Processes the update contact form, картика и кнопка сохран  из формы
    @PostMapping("/process-contact-update")
    public String processUpdateContactHandler(@ModelAttribute("contact") Contact contact,
                                              @RequestParam("profileImage") MultipartFile file,
                                              Model model,
                                              Principal principal,
                                              HttpSession session) {
        try {
//old
            Contact prevContact = contactService.getContactById(contact.getCid());
            String prevImage = prevContact.getImage();

            User user = userService.getUserByUsername(principal.getName());
            contact.setUser(user);

            //check if new img uploaded-same
            if (file.isEmpty()) {
                contact.setImage(prevImage);
            } else {
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            //save update contact to db
            contactService.saveContact(contact);
            session.setAttribute("message", new Message("Contact updated successfully!", "alert-success"));
        } catch (Exception e) { // if error
            session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
        }
        return "redirect:/user/show-contacts/0";
    }


    //профиль текущкго аутефиц польз ,из базы
    @GetMapping("/profile")
    public String profileHandler(Model model,
                                 Principal principal,
                                 HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        //заголовок в модел юзер в html
        model.addAttribute("title", "User Profile");

        return "prof/profile";
    }


    //страница профиля редакции
    @GetMapping("/update-profile")
    public String updateProfileHandler(Model model) {

        model.addAttribute("title", "Edit Profile");

        return "prof/update_profile_form";
    }


    //  редактирования профиля,   имени, информации "О себе" и изображения
    //
    @PostMapping("/process-update-profile")
    public String processEditProfileForm(@RequestParam("name") String name,
                                         @RequestParam("about") String about,
                                         @RequestParam("profileImage") MultipartFile file,
                                         Principal principal,
                                         HttpSession session) throws IOException {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        user.setName(name);
        user.setAbout(about);


        //if new img OK
        if (!file.isEmpty()) {

            if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")
                    || file.getContentType().equals("image/jpg")) {
                // then updtae user prof
                user.setImageUrl(file.getOriginalFilename());
                //save
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        //DB
        userService.saveUser(user);
        session.setAttribute("message", new Message("Profile updated successfully!", "alert- success"));

        return "prof/profile";
    }


    //настройки пользователя  стр= рользователь из бд текущего по имени
    @GetMapping("/settings")
    public String openSettings(Model model,
                               Principal principal,
                               HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        model.addAttribute("title", "Settings");

        return "prof/settings";
    }


    //This method processes the user's password change request
    //Обрабатывает изменение пароля пользователя. Если старый пароль совпадает с текущим, новый пароль шифруется и сохраняется.

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Principal principal,
                                 HttpSession session) {

        User user = userService.getUserByUsername(principal.getName());
        //get the current password stored in db
        String existingPassword = user.getPassword();


        //check if the old one
        if (passwordEncoder.matches(oldPassword, existingPassword)) {
            //if yes- encode the new password and update
            user.setPassword(passwordEncoder.encode(newPassword));

            userService.saveUser(user);

            session.setAttribute("message", new Message("Password is updated successfully!", " alert-success"));
        } else {
            session.setAttribute("message", new Message("Incorrect password!", "alert-danger"));
            return "redirect:/user/settings";
        }
        return "redirect:/user/dashboard";  //
    }


    // This method shows the form for recovering a forgotten password
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }


    //Обрабатывает ввод email для восстановления пароля, отображает секретный вопрос
    @PostMapping("/forgot-password")
    public String processForgotPasswordForm(@RequestParam("email") String email,
                                            Model model) {
        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "No account found with this email address.");
            return "forgot_password_form";
        }
        model.addAttribute("email", email);
        model.addAttribute("secretQuestion", user.getSecretQuestion());
        return "verify_secret_question";
    }


    // Проверяет ответ на секретный вопрос. Если ответ правильный, отображается форма для сброса пароля
    //This method checks the answer to the secret question and, if correct, shows the password reset form.
    @PostMapping("/verify-secret-question")
    public String verifySecretQuestion(@RequestParam("email") String email,
                                       @RequestParam("secretAnswer") String secretAnswer,
                                       Model model) {
        User user = userService.findByEmail(email);

        //if not
        if (user == null || !user.getSecretAnswer().equalsIgnoreCase(secretAnswer)) {
            model.addAttribute("error", "Invalid answer to the secret question.");
            model.addAttribute("email", email);
            model.addAttribute("secretQuestion", user.getSecretQuestion());

            return "verify_secret_question";
        }
//передача емейл в модель= проверка ответа / изменение пароля
        model.addAttribute("email", email);
        //the view to reset the password
        return "reset_password_form";
    }


    //This method handles the password reset and saves the new password in encrypted form/сброс
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                Model model) {

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "No account found with that email address.");
            return "forgot_password_form";
        }
//success
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);

        model.addAttribute("message", "Password successfully updated");
        return "login";
    }
}
