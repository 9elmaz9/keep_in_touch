package com.example.keep_in_touch.config;


import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



// Configuration class for setting up the admin user/ конфигурации, которая отвечает за настройку администратора в приложении

@Configuration //  он используется для определения бинов и другой конфигурации для Spring-приложения
public class AdminUserSetup {

    @Autowired // внедрить экземпляры
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    // Creates the admin user if it does not already exist in the database
    //Этот метод возвращает объект типа CommandLineRunner, который выполняется при запуске приложения
    // проверяется, существует"admin@example.com" если  нет, создается новый админ автоматом

    @Bean
    public CommandLineRunner createAdminUser(){
        return args-> { // выполняет логику после запуска приложения
            if (userRepository.findByEmail("admin@example.com") == null) {
                //if not -> create new one
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setName("Administrator");
                //Шифрование и установка пароля администратора
                admin.setPassword(passwordEncoder.encode("Elmaz_09091995"));
                admin.setRole("ROLE_ADMIN");
                admin.setEnabled(true);
                //save
                userRepository.save(admin);
            }
        };
    }
}
