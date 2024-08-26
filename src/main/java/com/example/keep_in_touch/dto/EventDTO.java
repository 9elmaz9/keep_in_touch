package com.example.keep_in_touch.dto;

import java.util.Date;
//dataa transfer object for event
//EventDTO служит для передачи данных о событиях между различными частями системы,
// такими как слои сервиса и представления. Он обеспечивает удобный и безопасный способ обмена данными о событиях,
// минимизируя риск утечки конфиденциальной информации или нарушений инкапсуляции.

public class EventDTO {

    private Long id;
    private String title;
    private Date start;
    private String note;
    private int userId;
    private int contactId;
    private String contactName;


    // Getter and setter methods for each attribute of the EventDTO class

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
