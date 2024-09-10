package com.example.keep_in_touch.dto;


//data transfer object -  used to encapsulate data for transfer between different layers
//используется для передачи данных между различными частями системы (например, между контроллером и сервисом)
// и может включать только те данные, которые необходимы для определенного случая использования.
//Это помогает улучшить безопасность и производительность системы.

public class UserDTO {

    private int id;
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean enabled; //Flag indicating whether the user is active
    private String imageUrl;
    private String about;
    private int validated; //did the user pass the verification
    private String secretQuestion;
    private String secretAnswer;


    // Getter and setter methods for each attribute of the UserDTO class


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

    public int getValidated() {
        return validated;
    }

    public void setValidated(int validated) {
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
}
