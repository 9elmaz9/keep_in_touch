package com.example.keep_in_touch.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.transaction.reactive.GenericReactiveTransaction;


//Entity class for User

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @NotBlank(message = "Name field is required")
    //It is mandatory (cannot be blank) and must be between 2 and 20 characters long.
    @Size(min = 2, max = 20, message = "minimim 2 and maximum 20 characters are allowed!")
    private String name;
    @Column(unique = true)  // It must be unique in the database and is mandatory.
    @NotBlank(message = "email field is required!")
    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email !!!")
    private String email;
    @NotBlank(message = "Password should not be empty!")
//It is mandatory and must be at least 8 characters long.
    @Size(min = 8, message = "Password should be at least 8 characters long!")
//The password is validated against a regular expression to ensure it is strong.
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,}$", message = "Password should be strong !!!")
    private String password;
    private String role;

    // Indicates whether the user is enabled (active) or not.
    private boolean enabled;

    // The URL or path to the user's profile image.
    @Column(name = "image_url")
    private String imageUrl;

    // A brief description or bio about the user.
    @Column(length = 500)
    private String about;

    // An integer field indicating whether the user has been validated.
    private Integer validated;

    @Column(name = "secret_question")
    private String secretQuestion;

    @Column(name = "secret_answer")
    private String secretAnswer;


    /**
     * One-to-many relationship with the Contact entity.
     * A user can have multiple contacts.
     * CascadeType.ALL means that all operations (e.g., persist, remove) on User will cascade to its contacts.
     * FetchType.LAZY means that contacts are loaded on demand.
     * The "user" field in the Contact entity is used to map this relationship.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Contact> contacs = new ArrayList<>();

    @Column(unique = true)
    //The unique username of the user.
    private String username;

    // Getter and setter methods for each attribute of the User class.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getValidated() {
        return validated;
    }

    public void setValidated(Integer validated) {
        this.validated = validated;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public List<Contact> getContacs() {
        return contacs;
    }

    public void setContacs(List<Contact> contacs) {
        this.contacs = contacs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
