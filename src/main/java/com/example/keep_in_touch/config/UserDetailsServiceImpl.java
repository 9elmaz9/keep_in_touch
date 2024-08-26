package com.example.keep_in_touch.config;


import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Implementation of UserDetailsService to load user-specific data
//Этот класс реализует интерфейс UserDetailsService, который используется Spring Security для загрузки данных пользователя, необходимых для аутентификации
@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    // Метод загружает пользователя по его имени (username). Если пользователь найден,возвращает объект UserDetails,
    // который содержит информацию о пользователе.Если пользователь не найден, выбрасывается исключение UsernameNotFoundException

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      // получение пользователя из базы даных по его имени
        User user = userRepository.getUserByUserName(username);

       //если нет ->
        if(user == null) {
            throw new UsernameNotFoundException("Username not found!");
        }
        //возвращает объект CustomUserDetails,который реализует интерфейс UserDetails
        return new CustomUserDetails(user);
    }
}
