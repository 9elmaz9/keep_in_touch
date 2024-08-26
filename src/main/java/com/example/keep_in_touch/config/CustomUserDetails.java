package com.example.keep_in_touch.config;

import com.example.keep_in_touch.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


//A tailored implementation of UserDetails to encapsulate the user's authentication and authorization details
//Этот класс используется для представления информации о пользователе, которая будет использоваться Spring Security
// для аутентификации и авторизации. Он реализует интерфейс UserDetails
public class CustomUserDetails implements UserDetails {

    // user data
    private User user;


    //Конструктор, который принимает объект User и инициализирует поле user
    public CustomUserDetails(User user) {
        super();
        this.user = user;
    }

    // Метод, который возвращает права доступа (роли) пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Создание объекта SimpleGrantedAuthority на основе роли пользователя
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
        // Возвращает список ролей пользователя (в данном случае только 1 роль)
        return List.of(simpleGrantedAuthority);
    }
    //SimpleGrantedAuthority — это конкретная реализация интерфейса GrantedAuthority в Spring Security. Она используется для представления одной роли или права пользователя в виде строки. Например, если пользователь имеет роль "ADMIN", то объект SimpleGrantedAuthority будет хранить это значение и возвращать его при необходимости.




    // Метод для получения пароля пользователя
    @Override
    public String getPassword() {
        return user.getPassword();
    }


    // Метод для получения имени пользователя, здесь используется email как имя пользователя
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // Метод проверяет, не истек ли срок действия учетной записи. Возвращает true, так как срок действия не истек
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    // Метод проверяет, не заблокирована ли учетная запись. Возвращает true, так как учетная запись не заблокирована
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    // Метод проверяет, не истек ли срок действия учетных данных (например, пароля). Возвращает true, так как срок действия не истек
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    // Метод проверяет, включена ли учетная запись (активна ли она). Возвращает true, так как учетная запись включена
    @Override
    public boolean isEnabled() {
        return true;
    }
}
