package org.example.presenters.repository;

import org.example.models.requests.AdminGroup;
import org.example.models.requests.Chat;
import org.example.models.requests.Message;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.database.DatabaseConnector;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ChatRepository {
    private final DatabaseConnector connector = DatabaseConnector.getConnector();

    public void addChat(Chat chat) throws SQLException {
        String query = "INSERT INTO chat (is_group_chat, timestamp) VALUES (?, ?)";
        Connection connection = connector.startConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setBoolean(1, chat.isGroupChat());
        statement.setTimestamp(2, chat.getTimestamp());
        statement.executeUpdate();
        statement.close();
        connector.closeConnection();
    }

    public Chat getChat(int chat_id) throws SQLException {
        String query = "SELECT * FROM chat WHERE chat_id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, chat_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Chat chat = new Chat();
                    chat.setChatId(resultSet.getInt("chat_id"));
                    chat.setGroupChat(resultSet.getBoolean("is_group_chat"));
                    chat.setTimestamp(resultSet.getTimestamp("timestamp"));
                    return chat;
                } else {
                    return null;
                }
            }
        }
    }

    public ResponseCodes updateChat(Chat chat) throws SQLException {
        String query = "UPDATE chat SET is_group_chat=?, timestamp=? WHERE chat_id=?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, chat.isGroupChat());
            statement.setTimestamp(2, chat.getTimestamp());
            statement.setInt(3, chat.getChatId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0)
                return ResponseCodes.NOT_UPDATED;
            return ResponseCodes.SUCCESS;
        }
    }

    public ResponseCodes deleteChat(int chat_id) throws SQLException {
        String query = "DELETE FROM chat WHERE chat_id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, chat_id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0)
                return ResponseCodes.SUCCESS;
            return ResponseCodes.NOT_DELETED;
        }
    }

    public int getPVUsersOnAGroup (int chat_id) throws SQLException {
        String query = "SELECT COUNT(chat_id) FROM chat WHERE is_group_chat=false AND chat_id IN (" +
                "SELECT chat_id FROM group_members WHERE user_id IN (SELECT user_id FROM group_members " +
                "WHERE chat_id=?))";

        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, chat_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    return 0;
                }
            }
        }
    }
}
