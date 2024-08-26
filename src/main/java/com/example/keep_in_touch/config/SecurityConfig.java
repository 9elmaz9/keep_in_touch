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

    //provides a custom UserDetailService impl
    //Beans are created to process users and encrypt their passwords
    @Bean // for  PasswordEncoder, AuthenticationManager, UserDetailsService
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImpl(); // является реализацией интерфейса UserDetailsService,используем кастомную реализацию
        // чтобы получить данные пользователя из базы данных
    }



    //Provides a BCryptPasswordEncoder for password encryption
    //Этот метод создает и возвращает экземпляр BCryptPasswordEncoder, который используется для шифрования паролей
    @Bean
    BCryptPasswordEncoder getPasswordEncoder() { //represent  encode()  для хеширования паролей  and matches() прверка соответствия
        return new BCryptPasswordEncoder();
    }



    //Provides an authentication provider that uses a custom UserDetailsService and BCryptPasswordEncoder.
    //Этот метод создает и настраивает DaoAuthenticationProvider, который используется для аутентификации пользователей
    //Он использует UserDetailsService для загрузки данных пользователя  db и PasswordEncoder для проверки паролей.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //creating an instance of
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService()); //setting the custom UDS
        daoAuthenticationProvider.setPasswordEncoder(this.getPasswordEncoder()); //setting the BCPE
        return daoAuthenticationProvider;// an instance of DaoAuthenticationProvider
    }



    //Configures the security settings for the application
    //Этот метод конфигурирует правила безопасности для веб-приложения с использованием HttpSecurity,такие как авторизация, аутентификация, CSRF-защита и форма входа
    //Конфигурирует доступ к различным URL-адресам на основе ролей пользователей и настраивает страницу входа.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //off protect = csrf - beter only for test
        //Отключает защиту от CSRF (Cross-Site Request Forgery). Это не рекомендуется для продакшена, но может быть необходимо для тестирования или API.
        http.csrf(csrf -> csrf.disable())
                // настройка авторизации для различныз URL
                .authorizeHttpRequests(auth -> auth
                       //доступ только для админ
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                       // доступ только для ролей юзер и админ
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                        // разрешить доступ ко всем остальным урл ддля всех пользователей
                        .requestMatchers("/**").permitAll()
                )

                //форма входа - настраиваем параметры
                .formLogin(formLogin -> formLogin
                        .loginPage("/signin") // указывае  кастомную страницу для входа

                        .loginProcessingUrl("/do-login") // урл для обратока данных входа

                        .defaultSuccessUrl("/user/dashboard") //перенаправление на дашборд после успешного входа
                        .permitAll() // разрешает всем пользователям доступ к странице
                )
                //Включение базовой HTTP аутентификации - //Базовая HTTP аутентификация — это метод проверки учетных данных (логина и пароля) через заголовки HTTP.
                .httpBasic(withDefaults -> {
                })

                ////Указывает кастомный DaoAuthenticationProvider для использования при аутентификации
                .authenticationProvider(authenticationProvider());


        //Метод возвращает SecurityFilterChain, который содержит всю конфигурацию безопасности для приложения
        return http.build();


    }


}
