package com.example.keep_in_touch.helper;


//Класс, представляющий сообщение с содержимым и типом. передачи сообщений между различными слоями приложения,
//например, из контроллера в представление
public class Message {
    private String content;

    private String type;


    public Message() {
    }

    // new message
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
