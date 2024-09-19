package com.example.keep_in_touch.config;


import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// Configuration class for setting up the admin user

@Configuration
public class AdminUserSetup {

    @Autowired // внедрить экземпляры
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    // Creates the admin user if it does not already exist in the database
    // метод возвращает объект типа CommandLineRunner, который выполняется при запуске приложения

    @Bean
    public CommandLineRunner createAdminUser() {
        return args -> { // выполняет логику после запуска приложения
            if (userRepository.findByEmail("admin@example.com") == null) {
                //if not -> create
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setName("Administrator");

                //Шифрование и установка пароля администратора
                admin.setPassword(passwordEncoder.encode("Password1995"));
                admin.setRole("ROLE_ADMIN");
                admin.setEnabled(true);

                userRepository.save(admin);
            }
        };
    }
}
