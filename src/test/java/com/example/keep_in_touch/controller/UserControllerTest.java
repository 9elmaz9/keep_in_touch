package com.example.keep_in_touch.controller;

import com.example.keep_in_touch.dto.UserDTO;
import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.mapper.UserMapper;
import com.example.keep_in_touch.service.ContactService;
import com.example.keep_in_touch.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class) //подключает расширение Mockito для работы с моками.
public class UserControllerTest {

    //Используются мок-объекты для зависимости контроллера и MockMvc для имитации HTTP-запросов и проверки ответов
    @Mock
    private UserService userService;
    @Mock //  используется для создания мок-объектов.
    private ContactService contactService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;

    //создаёт экземпляр UserController и автоматически внедряет в него все мок-объекты.
    @InjectMocks
    private UserController userController;

    // MockMvc для имитации HTTP-запросов и проверки ответов
    private MockMvc mockMvc;
    private User user;
    private UserDTO userDTO;
    private Contact contact;

    //запскается до всего теста что бы внедрить туда данные
    @BeforeEach
    void setUp() {
        // инициализация  MockMvc для теста
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // создаем юзера для теста
        user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("elmazworkakk@gmail.com");

        // cоздаем дто
        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("Test User ");
        userDTO.setEmail("elmazworkakk@gmail.com");

        // создаем Contact
        contact = new Contact();
        contact.setCid(1);
        contact.setName("Test Contact");
        contact.setUser(user);

        // конфигурируем mock для возвращения данных
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);


    }



    // тест отображения панели управления пользователя
    @Test
    void testDashboard() throws Exception {
        mockMvc.perform(get("/user/dashboard") // симулировать HTTP GET
                        .principal(() -> "elmazworkakk@gmail.com"))
                .andExpect(status().isOk())  // 200 ок
                .andExpect(view().name("prof/user_dashboard"))
                .andExpect(model().attributeExists("user"))  // проверка содержит ли модель user аттрибут
                .andExpect(model().attribute("user", userDTO));  // смотрим  user аtribute  матчит с юзер дто-модель должна содержать именно тот объект, который был подготовлен в рамках теста.
    }

    // тестим форму  которая отображает форму добавления контакта
    @Test
    void testAddContactForm() throws Exception {
        mockMvc.perform(get("/user/add-contact-form")
                        .principal(() -> "elmazworkakk@gmail.com"))
                //checks
                .andExpect(status().isOk())  //  is 200 OK
                .andExpect(view().name("prof/add_contact_form"))
                .andExpect(model().attributeExists("contact"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attribute("title", "Add Contact"));
    }

    // тест добавления контакта
    @Test
    void testProcessContactForm() throws Exception {
        // Setup a mock file
        MockMultipartFile mockFile = new MockMultipartFile("profileImage", "test.jpg", "image/jpeg", "test data".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/process-contact")
                        .file(mockFile)
                        .principal(() -> "elmazworkakk@gmail.com")
                        .param("name", "Test Contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("prof/add_contact_form"))
                .andExpect(model().attributeExists("contact"))  // Проверяем, что модель содержит атрибут "contact"
                .andExpect(request().sessionAttribute("message", org.hamcrest.Matchers.notNullValue()));
    }


    // тест формы контактного листа который отображает список контактов пользователя с поддержкой пагинации
    @Test
    void testShowContactsHandler() throws Exception {
        // создаем список контактов и помещает его в объект Page
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        Page<Contact> contactsPage = new PageImpl<>(contacts); //Page- представляет собой страницу результатов

        // Настраивает фиктивный contactService для возврата contactsPage
        when(contactService.findContactsByUserId(anyInt(), any(Pageable.class))).thenReturn(contactsPage);

        mockMvc.perform(get("/user/show-contacts/0")
                        .principal(() -> "elmazworkakk@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("prof/show_contacts"))
                .andExpect(model().attributeExists("contacts"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attribute("contacts", contactsPage));  // Checks if the "contacts" attribute matches contactsPage
    }

    // отображает подробную информацию о контакте
    @Test
    void testHandleContactDetail() throws Exception {
        // Configures the contactService mock to return the contact
        when(contactService.getContactById(anyInt())).thenReturn(contact);

        mockMvc.perform(get("/user/1/contact")
                        .principal(() -> "elmazworkakk@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("prof/contact_details"))
                .andExpect(model().attributeExists("contact"))
                .andExpect(model().attribute("contact", contact));
    }

    // отображает профиль пользователя.
    @Test
    void testProfileHandler() throws Exception {
        mockMvc.perform(get("/user/profile")
                        .principal(() -> "elmazworkakk@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("prof/profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("title", "User Profile"));
    }

}

//Возвращает статус 200 OK.
//Загружает и отображает правильное представление ("prof/user_dashboard").
//Включает в модель атрибут "user".
//Атрибут "user" в модели совпадает с заранее подготовленным объектом userDTO.