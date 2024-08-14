package com.example.keep_in_touch.repository;

import com.example.keep_in_touch.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository {
    //return a list of even
    List<Event> findByUserId(int userId);

   // Page<Event> findByUserId(Long userId, Pageable pageable);

    List<Event>findByDate(LocalDate date);
}
