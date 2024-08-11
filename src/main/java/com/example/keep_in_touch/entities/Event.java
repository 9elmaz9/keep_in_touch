package com.example.keep_in_touch.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

/**
 * Entity class for Event.
 * This class represents an event in the personal contact manager application.
 * It is mapped to the "events" table in the database.
 */
@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String title;
    private Date start;
    private String note;
    private String category;
@ManyToOne  //     Each event is associated with one user who owns it.The user_id column in the "events" table is used as the foreign key. FK
@JoinColumn(name = "user_id")
@JsonIgnore
    private User user;



@ManyToOne      // Each event is associated with one contact.The contact_id column in the "events" table is used as the foreign key.

@JoinColumn(name = "contact_id")
    private Contact contact;



    // Getter and setter methods for each attribute of the Event class.


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
