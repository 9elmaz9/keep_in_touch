package com.example.keep_in_touch.repository;

import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


// * Repository interface for managing Contact entities.
// * CRUD (создание, чтение, обновление, удаление) с сущностями Contact.

public interface ContactRepository  extends JpaRepository<Contact, Integer> {

    // Находит страницу контактов для конкретного пользователя по его ID -Finds a page/ return  page contact  with pageable
    @Query("from Contact c where c.user.id = :userId") //JPQL
    Page<Contact> findContactsByUserId(@Param("userId") int userId, Pageable pageable);

    //Finds a list of contacts for a specific user by user ID / return LIst - all contact n 1 time
    @Query("from Contact c where c.user.id = :userId")
    List<Contact> findContactsByUserId(@Param("userId") int userId);

    //search -name and user / return a  contact list
    //Находит контакты, имя которых содержит заданную строку, для конкретного пользователя
    List<Contact> findByNameContainingAndUser(String name, User user);

    //Находит все контакты для конкретного пользователя
    //Finds contacts for a specific user
    List<Contact> findByUser(User user);


}
