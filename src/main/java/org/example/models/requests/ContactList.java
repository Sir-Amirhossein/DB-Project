package org.example.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactList {
    @JsonProperty("id")
    private int id;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("contact_id")
    private int contactId;

    public ContactList(int id, int userId, int contactId) {
        this.id = id;
        this.userId = userId;
        this.contactId = contactId;
    }

    public ContactList() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
