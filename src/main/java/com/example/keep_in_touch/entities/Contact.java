package com.example.keep_in_touch.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;
@Entity
@Table(name="contacts")
public class Contact {
    /**
     * Entity class for Contact.
     * This class represents a contact in the personal contact manager application.
     * It is mapped to the "contacts" table in the database.
     */
@Id  //PK
@GeneratedValue(strategy = GenerationType.AUTO) // value PK
@Column(name="contact_id")
    private  int cid;
    private String name;
    @Column(name = "nick_name")
    private String nickName;
    private String work;
    private String email;
    private boolean phone;
    private String image;
    @Column(length = 50000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id") // conect with user
    @JsonIgnore     // The @JsonIgnore annotation prevents this field from being serialized into JSON.
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    //orphanRemoval = true ensures that if an Event is removed from the Set, it will be deleted from the database.
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    public Contact() {
        super();
    }


    // Getter and setter methods for each attribute of the Contact class.

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
