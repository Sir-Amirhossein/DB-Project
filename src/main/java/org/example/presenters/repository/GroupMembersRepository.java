package org.example.presenters.repository;

import org.example.models.requests.AdminGroup;
import org.example.models.requests.GroupMembers;
import org.example.models.requests.Message;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.database.DatabaseConnector;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class GroupMembersRepository {
    private final DatabaseConnector connector = DatabaseConnector.getConnector();

    public void addGroupMembers(GroupMembers groupMembers) throws SQLException {
        String query = "INSERT INTO group_members (chat_id, user_id) VALUES (?, ?)";
        Connection connection = connector.startConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, groupMembers.getChatId());
        statement.setInt(2, groupMembers.getUserId());
        statement.executeUpdate();
        statement.close();
        connector.closeConnection();
    }

    public GroupMembers getGroupMembers(int id) throws SQLException {
        String query = "SELECT * FROM group_memebers WHERE id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    GroupMembers groupMembers = new GroupMembers();
                    groupMembers.setId(resultSet.getInt("id"));
                    groupMembers.setChatId(resultSet.getInt("chat_id"));
                    groupMembers.setUserId(resultSet.getInt("user_id"));
                    return groupMembers;
                } else {
                    return null;
                }
            }
        }
    }

    public ResponseCodes updateGroupMembers(GroupMembers groupMembers) throws SQLException {
        String query = "UPDATE group_members SET chat_id=?, user_id=? WHERE id=?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, groupMembers.getChatId());
            statement.setInt(2, groupMembers.getUserId());
            statement.setInt(3, groupMembers.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0)
                return ResponseCodes.NOT_UPDATED;
            return ResponseCodes.SUCCESS;
        }
    }

    public ResponseCodes deleteGroupMembers(int id) throws SQLException {
        String query = "DELETE FROM group_members WHERE id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0)
                return ResponseCodes.SUCCESS;
            return ResponseCodes.NOT_DELETED;
        }
    }
}
