package org.example.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupMembers {
    @JsonProperty("id")
    private int id;
    @JsonProperty("chat_id")
    private int chatId;
    @JsonProperty("user_id")
    private int userId;

    public GroupMembers(int id, int chatId, int userId) {
        this.id = id;
        this.chatId = chatId;
        this.userId = userId;
    }

    public GroupMembers() {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
