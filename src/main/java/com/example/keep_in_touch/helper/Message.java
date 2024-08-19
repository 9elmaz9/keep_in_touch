package com.example.keep_in_touch.helper;


//Класс, представляющий сообщение с содержимым и типом. передачи сообщений между различными слоями приложения,
//например, из контроллера в представление
public class Message {
    // содержимое сообщение
    private String content;

    //тип сообщения /  ок не ок
    private String type;


    // default constructor
    public Message() {
    }

    //constructor a new message
    public Message(String content, String type) {
        super();
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
