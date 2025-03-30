package org.example.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminGroup {
    @JsonProperty("id")
    private int id;
    @JsonProperty("chat_id")
    private int chatId;
    @JsonProperty("admin_id")
    private int adminId;

    public AdminGroup(int id, int chatId, int adminId) {
        this.id = id;
        this.chatId = chatId;
        this.adminId = adminId;
    }

    public AdminGroup() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
