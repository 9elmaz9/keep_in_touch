package com.example.keep_in_touch.config;


//UserDetailsService — это интерфейс, который позволяет Spring Security загружать информацию о пользователе в процессе аутентификации.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//A configuration class designed to set up security settings for the application.
//@Configuration: Объявляет класс конфигурационным.
//@EnableWebSecurity: Включает функциональность Spring Security.
//@Bean: Определяет метод, возвращающий объект, который Spring будет управлять как бином.





@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //provides a custom UserDetailService impl.Beans are created to process users and encrypt their passwords
    @Bean
    // for  PasswordEncoder, AuthenticationManager, UserDetailsService
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImpl();
        // чтобы получить данные пользователя из базы данных
    }

    //Provides a BCryptPasswordEncoder for password encryption
    @Bean
    // создает обьект для шифрования паролей, внутри метожа и возвращает его 10,09 в бине , бин созжает оюбект
    BCryptPasswordEncoder getPasswordEncoder() { //represent  encode()  для хеширования паролей  and matches() прверка соответствия
        return new BCryptPasswordEncoder();
    }

    //Provides an authentication provider that uses a custom UserDetailsService and BCryptPasswordEncoder.
    @Bean
    //создает оббект для аутентификации пользователей используя инфу от юзер детейил сервис  и бискрипт пасвор енкодер для проверки правильности пароля
    public DaoAuthenticationProvider authenticationProvider() {
        //creating an instance of
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService()); //setting the custom UDS
        daoAuthenticationProvider.setPasswordEncoder(this.getPasswordEncoder()); //setting the BCPE
        return daoAuthenticationProvider;// an instance of DaoAuthenticationProvider
    }


    //Configures the security settings for the application
    @Bean
    // настраивает правила безопастностт к разным частям сайта и форма входа
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  //   analog   http.csrf(AbstractHttpConfigurer::disable)
                // настройка авторизации для различныз URL
                .authorizeHttpRequests(auth -> auth
                        //доступ
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/**").permitAll()
                )

                //форма входа - настраиваем параметры
                .formLogin(formLogin -> formLogin
                        .loginPage("/signin")

                        .loginProcessingUrl("/do-login")

                        .defaultSuccessUrl("/user/dashboard")
                        .permitAll()
                )
                 //Базовая HTTP аутентификация — это метод проверки учетных данных (логина и пароля) через заголовки HTTP.
                .httpBasic(withDefaults -> {
                })

                //Указывает кастомный DaoAuthenticationProvider для использования при аутентификации
                .authenticationProvider(authenticationProvider());


        // возвращает SecurityFilterChain, который содержит всю конфигурацию безопасности для приложения
        return http.build();


    }


}
