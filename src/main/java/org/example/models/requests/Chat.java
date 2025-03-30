package org.example.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;

public class Chat {
    @JsonProperty("chat_id")
    private int chatId;
    @JsonProperty("is_group_chat")
    private boolean isGroupChat;
    @JsonProperty("timestamp")
    private Timestamp timestamp;

    public Chat(int chatId, boolean isGroupChat, Timestamp timestamp) {
        this.chatId = chatId;
        this.isGroupChat = isGroupChat;
        this.timestamp = timestamp;
    }

    public Chat() {
        super();
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public void setGroupChat(boolean groupChat) {
        isGroupChat = groupChat;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
