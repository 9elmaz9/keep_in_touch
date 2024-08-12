package com.example.keep_in_touch.repository;

import com.example.keep_in_touch.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository   //for managing User entities
public interface UserRepository extends JpaRepository<User,Integer> {  // CRUD (создание, чтение, обновление, удаление)

    //uses JPQL(Java Persistence Query Language) to find a user by email
    @Query("select u from User u where u.email = :email")
    User getUserByUserName(@Param("email")String email);

    //autO  generate  a query that searches for all users whose names contain the given string
    List<User> findByNameContaining(String name);


    //for getting all users with pagination support,broken down into pages
    @Query("from User u")
    Page<User> findAllUsers(Pageable pageable);

    //uses Spring Data's built-in capability to find a user by their email
    User findByEmail(String email);

    //same as findAllUsers , uses JPQL
    @Query("from User u")
    Page  findAll(Pageable pageable);


    //searches for a user by their email and secret question/useful for password recovery or user verification functions
    // @return the user with the specified email and secret question
    User findByEmailAndSecretQuestion(String email,String secretQuestion);

}
