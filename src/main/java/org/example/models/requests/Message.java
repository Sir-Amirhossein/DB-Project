package org.example.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;

public class Message {
    @JsonProperty("message_id")
    private int messageId;
    @JsonProperty("chat_id")
    private int chatId;
    @JsonProperty("sender_id")
    private int senderId;
    @JsonProperty("message_text")
    private String messageText;
    @JsonProperty("time")
    private Timestamp timestamp;

    public Message(int messageId, int chatId, int senderId, String messageText, Timestamp timestamp) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public Message() {
        super();
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
