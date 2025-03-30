package org.example.presenters.repository;

import org.example.models.requests.AdminGroup;
import org.example.models.requests.Message;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.database.DatabaseConnector;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AdminGroupRepository {
    private final DatabaseConnector connector = DatabaseConnector.getConnector();

    public void addAdminGroup(AdminGroup adminGroup) throws SQLException {
        String query = "INSERT INTO admin_group (chat_id, admin_id) VALUES (?, ?)";
        Connection connection = connector.startConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, adminGroup.getChatId());
        statement.setInt(2, adminGroup.getAdminId());
        statement.executeUpdate();
        statement.close();
        connector.closeConnection();
    }

    public AdminGroup getAdminGroup(int id) throws SQLException {
        String query = "SELECT * FROM admin_group WHERE id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    AdminGroup adminGroup = new AdminGroup();
                    adminGroup.setId(resultSet.getInt("id"));
                    adminGroup.setAdminId(resultSet.getInt("admin_id"));
                    adminGroup.setChatId(resultSet.getInt("chat_id"));
                    return adminGroup;
                } else {
                    return null;
                }
            }
        }
    }

    public ResponseCodes updateAdminGroup(AdminGroup adminGroup) throws SQLException {
        String query = "UPDATE admin_group SET chat_id=?, admin_id=? WHERE id=?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, adminGroup.getChatId());
            statement.setInt(2, adminGroup.getAdminId());
            statement.setInt(3, adminGroup.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0)
                return ResponseCodes.NOT_UPDATED;
            return ResponseCodes.SUCCESS;
        }
    }

    public ResponseCodes deleteAdminGroup(int id) throws SQLException {
        String query = "DELETE FROM admin_group WHERE id = ?";
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