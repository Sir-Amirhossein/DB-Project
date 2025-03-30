package org.example.presenters.repository;

import org.example.models.requests.AdminGroup;
import org.example.models.requests.Message;
import org.example.models.requests.UserAccount;
import org.example.models.responses.ResponseCodes;
import org.example.models.responses.UserMessageCount;
import org.example.presenters.database.DatabaseConnector;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {
    private final DatabaseConnector connector = DatabaseConnector.getConnector();

    public void addMessage(Message message) throws SQLException {
        String query = "INSERT INTO message (chat_id, sender_id, message_text, time) VALUES" +
                " (?, ?, ?, ?)";
        Connection connection = connector.startConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, message.getChatId());
        statement.setInt(2, message.getSenderId());
        statement.setString(3, message.getMessageText());
        statement.setTimestamp(4, message.getTimestamp());
        statement.executeUpdate();
        statement.close();
        connector.closeConnection();
    }

    public Message getMessage(int message_id) throws SQLException {
        String query = "SELECT * FROM message WHERE message_id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, message_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Message message = new Message();
                    message.setMessageId(resultSet.getInt("message_id"));
                    message.setChatId(resultSet.getInt("chat_id"));
                    message.setSenderId(resultSet.getInt("sender_id"));
                    message.setMessageText(resultSet.getString("message_text"));
                    message.setTimestamp(resultSet.getTimestamp("time"));
                    return message;
                } else {
                    return null;
                }
            }
        }
    }

    public ResponseCodes updateMessage(Message message) throws SQLException {
        String query = "UPDATE message SET chat_id=?, sender_id=?, message_text=?, time=? WHERE message_id=?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, message.getChatId());
            statement.setInt(2, message.getSenderId());
            statement.setString(3, message.getMessageText());
            statement.setTimestamp(4, message.getTimestamp());
            statement.setInt(5, message.getMessageId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0)
                return ResponseCodes.NOT_UPDATED;
            return ResponseCodes.SUCCESS;
        }
    }

    public ResponseCodes deleteMessage(int message_id) throws SQLException {
        String query = "DELETE FROM message WHERE message_id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, message_id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0)
                return ResponseCodes.SUCCESS;
            return ResponseCodes.NOT_DELETED;
        }
    }
}