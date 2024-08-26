package com.example.keep_in_touch.repository;

import com.example.keep_in_touch.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;

// CRUD + используется для управления сущностями Event в базе данных

public interface EventRepository extends JpaRepository<Event , Long> { // long pk
    //return a list of even
    List<Event> findByUserId(int userId);

   // Page<Event> findByUserId(Long userId, Pageable pageable);

    // List<Event>findByDate(LocalDate date);
}
