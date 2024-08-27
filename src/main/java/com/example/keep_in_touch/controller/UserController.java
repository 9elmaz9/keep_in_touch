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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    /**
     * Метод добавляет данные о текущем пользователе в модель, чтобы они были доступны на всех страницах
     */
    @ModelAttribute
    public void addCommonData(Model model,
                              Principal principal) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", userMapper.toDTO(user));
    }


    /**
     * отображает главную страницу пользователя dashboard с инфо о текущем пользователе
     */
    @GetMapping("/dashboard")
    // principal: Предоставляет информацию о текущем аутентифицированном пользователе, в частности, его имя
    public String dashboard(Model model,
                            Principal principal,
                            HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", userMapper.toDTO(user));
        model.addAttribute("title", "User Dashboard");
        return "prof/user_dashboard";
    }


    /**
     * add-contact-form: Отображает форму для добавления нового контакта
     */
    @GetMapping("/add-contact-form")
    public String addContactForm(Model model,
                                 Principal principal,
                                 HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "prof/add_contact_form";
    }


    /**
     * process-contact: Обрабатывает форму добавления контакта. Загружает изображение профиля, сохраняет контакт,связывая его с текущим пользователем.
     */
    @PostMapping("/process-contact")
    public String processContactForm(@ModelAttribute Contact contact,
                                     @RequestParam("profileImage") MultipartFile file,
                                     Principal principal,
                                     HttpSession session,
                                     Model model) {

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
        return "prof/add_contact_form";
    }


    //Methods for displaying and managing contacts:

    /**
     * Отображает список контактов пользователя с поддержкой пагинации
     */
    @GetMapping("/show-contacts/{page}")
    //Shows the contacts for the logged-in user.
    public String showContactHandler(@PathVariable("page") Integer page,
                                     Model model,
                                     Principal principal,
                                     HttpSession session) {

        // Get the username of the logged-in user
        String username = principal.getName();
        // Find the user in the database using their username
        User user = userService.getUserByUsername(username);

        // Set up pagination to show 4 contacts per page
        Pageable pageable = PageRequest.of(page, 4);
        // Get the user's contacts for the current page
        Page<Contact> contacts = contactService.findContactsByUserId(user.getId(), pageable);


        // Add the contacts and other info to the model so the view can use it
        model.addAttribute("contacts", contacts);
        model.addAttribute("title", "All Contacts");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contacts.getTotalPages());

        return "prof/show_contacts";    // the contacts view

    }


    /**
     * Shows the details of a specific contact-Отображает подробную информацию о контакте
     */
    @GetMapping("/{cId}/contact")
    public String handleContactDetail(@PathVariable("cId") Integer cId,
                                      Model model,
                                      Principal principal,
                                      HttpSession session) {

        // Get the username of the logged-in user
        String username = principal.getName();
        //Find the user in the db using their username
        User user = userService.getUserByUsername(username);
        //Find the contact by its id
        Contact contact = contactService.getContactById(cId);

        //check if the contact belongs to the log-in usr
        if (user.getId() == contact.getUser().getId()) {
            //add contact to the model so the view can use it
            model.addAttribute("contact", contact);
        }

        //the contact details view
        return "prof/contact_details";
    }


    /**
     * Deletes a contact if the logged-in user is the owner-This method handles deleting a contact.
     */
    @GetMapping("/delete/{cId}")
    public String deleteContactHandler(@PathVariable("cId") Integer cId,
                                       Principal principal,
                                       HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        Contact contact = contactService.getContactById(cId);

        if (user.getId() == contact.getUser().getId()) {
            contactService.deleteContact(contact);

            // Set a success message in the session
            session.setAttribute("message", new Message("Contact deleted successfully!", "alert-success"));
        } else {
            //if not
            session.setAttribute("message", new Message("Permission rejected", "alert-danger"));
        }

        return "redirect:/user/show-contact/0";//the redirect to contacts view
    }


    /**
     * Shows the form for updating a contact-This method displays the form to update a contact
     */
    @PostMapping("/update-contact/{cId}")
    public String updateFormHandler(@PathVariable("cId") Integer cId,
                                    Model model) {

        //set title for the page
        model.addAttribute("title", " Update Contact");
        Contact contact = contactService.getContactById(cId);
        //add the contct to the model for using
        model.addAttribute("contact", contact);

        return "prof/update_contact_form";//to show the update form
    }


    /**
     * Обрабатывает форму обновления контакта, включая обновление изображения профиля
     * Processes the update contact form, including updating the profile image
     */
    @PostMapping("/process-contact-update")
    public String processUpdateContactHandler(@ModelAttribute("contact") Contact contact,
                                              @RequestParam("profileImage") MultipartFile file,
                                              Model model,
                                              Principal principal,
                                              HttpSession session) {
        try {
            // Find the existing contact by its ID
            Contact prevContact = contactService.getContactById(contact.getCid());
            String prevImage = prevContact.getImage();  // Get the previous profile image

            // Get the logged-in user
            User user = userService.getUserByUsername(principal.getName());
            // Set the user for the contact being updated
            contact.setUser(user);

            //check if new img uploaded
            if (file.isEmpty()) {
                contact.setImage(prevImage); // ifnot new- stay the same
            } else {
                //if new uploaded
                contact.setImage(file.getOriginalFilename());
                //save
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            //save update contact to db
            contactService.saveContact(contact);
            //+ ok message set
            session.setAttribute("message", new Message("Contact updated successfully!", "alert-success"));
        } catch (Exception e) { // if error - display this one message
            session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
        }
        return "redirect:/user/show-contacts/0"; //Redirect back to the contacts list
    }


    /**
     * Displays the user profile page
     */
    @GetMapping("/profile")
    public String profileHandler(Model model,
                                 Principal principal,
                                 HttpSession session) {

        String username = principal.getName(); //check logged in or not
        User user = userService.getUserByUsername(username);

        //set the title profile page
        model.addAttribute("title", "User Profile");

        //return the name of view to display the profile
        return "prof/profile";
    }


    /**
     * This method shows the form to edit the user's profile.
     */
    @GetMapping("/update-profile")
    public String updateProfileHandler(Model model) {
        //set title
        model.addAttribute("title", "Edit Profile");

        //to display update profile form
        return "prof/update_profile_form";
    }


    /**
     * Обрабатывает форму редактирования профиля, включая обновление имени, информации "О себе" и изображения профиля.
     * This method processes the form to update the user's profile.
     */
    @PostMapping("/process-update-profile")
    public String processEditProfileForm(@RequestParam("name") String name,
                                         @RequestParam("about") String about,
                                         @RequestParam("profileImage") MultipartFile file,
                                         Principal principal,
                                         HttpSession session) throws IOException {

        //get the username of the loggedin user
        String username = principal.getName();
        // find the user in the db
        User user = userService.getUserByUsername(username);
        // update the user's name & "About" info
        user.setName(name);
        user.setAbout(about);


        //if new img OK
        if (!file.isEmpty()) {
            //check valid format
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
        //save the updated Us inf to DB
        userService.saveUser(user);
        //set sms
        session.setAttribute("message", new Message("Profile updated successfully!", "alert- success"));
        //profilep page
        return "prof/profile";
    }


    //Manage password methods *


    /**
     * This method shows the user's settings page.
     */
    @GetMapping("/settings")
    public String openSettings(Model model,
                               Principal principal,
                               HttpSession session) {

        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        // set the title for settng page
        model.addAttribute("title", "Settings");

        // to display the settings
        return "prof/settings";
    }


    /**
     * This method processes the user's password change request
     * Обрабатывает изменение пароля пользователя. Если старый пароль совпадает с текущим, новый пароль шифруется и сохраняется.
     */
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Principal principal,
                                 HttpSession session) {

        User user = userService.getUserByUsername(principal.getName());
        //get the current password stored in db
        String existingPassword = user.getPassword();


        //check if the old one provided by the user matches the stored passwor
        if (passwordEncoder.matches(oldPassword, existingPassword)) {
            //if yes- encode the new password and update
            user.setPassword(passwordEncoder.encode(newPassword));
            //save update
            userService.saveUser(user);
            //set sms
            session.setAttribute("message", new Message("Password is updated successfully!", " alert-success"));
        } else {
            //if old one doesn't match - set error message
            session.setAttribute("message", new Message("Incorrect password!", "alert-danger"));
            // go back to the setting page
            return "redirect:/user/setting";
        }
        //if everything is OKAY  - then redirect to dashboard
        return "redirect:/user/dashboars";
    }


    //Methods for password recovery*

    /**
     * This method shows the form for recovering a forgotten password.
     */
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        //to display the forgot password form
        return "forgot_password_form";
    }


    /**
     * Обрабатывает ввод email для восстановления пароля, отображает секретный вопрос
     */
    @PostMapping("/forgot-password")
    public String processForgotPasswordForm(@RequestParam("email") String email,
                                            Model model) {
        //find the user in the db using their ameil
        User user = userService.findByEmail(email);
        //if user not found display error sms
        if (user == null) {
            model.addAttribute("error", "No account found with this email address.");
            //again display forgot password form
            return "forgot_password_form";
        }
        model.addAttribute("email", email);
        model.addAttribute("secretQuestion", user.getSecretQuestion());
        //the view to verify the secret question
        return "verify_secret_question";
    }


    /**
     * Проверяет ответ на секретный вопрос. Если ответ правильный, отображается форма для сброса пароля
     * This method checks the answer to the secret question and, if correct, shows the password reset form.
     */
    @PostMapping("/verify-secret-question")
    public String verifySecretQuestion(@RequestParam("email") String email,
                                       @RequestParam("secretAnswer") String secretAnswer,
                                       Model model) {
        User user = userService.findByEmail(email); // find user in db using their email

        //if not or not correct show an error sms
        if (user == null || !user.getSecretAnswer().equalsIgnoreCase(secretAnswer)) {
            model.addAttribute("error", "Invalid answer to the secret question.");
            model.addAttribute("email", email);
            model.addAttribute("secretQuestion", user.getSecretQuestion());

            // return to verify the secret question again
            return "verify_secret_question";
        }
        //if the answer is correct , pass the email to the model and show the password reset form
        model.addAttribute("email", email);
        //the view to reset the password
        return "reset_password_form";
    }


    /**
     * This method handles the password reset and saves the new password in encrypted form.
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                Model model) {

        User user = userService.findByEmail(email);

        //if the user not found, show an eeror sms and return to the forgot password form
        if (user == null) {
            model.addAttribute("error", "No account found with that email address.");
            return "forgot_password_form";
        }
        //encrypt the new password and set it for the user
        user.setPassword(passwordEncoder.encode(password));
        //save the update user info to the db
        userService.saveUser(user);

        //set a success message
        model.addAttribute("message", "Password successfully updated");
        // the login view
        return "login";
    }
}
