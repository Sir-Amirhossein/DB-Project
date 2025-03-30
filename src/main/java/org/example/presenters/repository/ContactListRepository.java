package org.example.presenters.repository;

import org.example.models.requests.AdminGroup;
import org.example.models.requests.ContactList;
import org.example.models.requests.Message;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.database.DatabaseConnector;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ContactListRepository {
    private final DatabaseConnector connector = DatabaseConnector.getConnector();

    public void addContact(ContactList contactList) throws SQLException {
        String query = "INSERT INTO contact_list (user_id, contact_id) VALUES (?, ?)";
        Connection connection = connector.startConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, contactList.getUserId());
        statement.setInt(2, contactList.getContactId());
        statement.executeUpdate();
        statement.close();
        connector.closeConnection();
    }

    public ContactList getContact(int id) throws SQLException {
        String query = "SELECT * FROM contact_list WHERE id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ContactList contactList = new ContactList();
                    contactList.setId(resultSet.getInt("id"));
                    contactList.setUserId(resultSet.getInt("user_id"));
                    contactList.setContactId(resultSet.getInt("contact_id"));
                    return contactList;
                } else {
                    return null;
                }
            }
        }
    }

    public ResponseCodes updateContact(ContactList contactList) throws SQLException {
        String query = "UPDATE contact_list SET user_id=?, contact_id=? WHERE id=?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, contactList.getUserId());
            statement.setInt(2, contactList.getContactId());
            statement.setInt(3, contactList.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0)
                return ResponseCodes.NOT_UPDATED;
            return ResponseCodes.SUCCESS;
        }
    }

    public ResponseCodes deleteContact(int id) throws SQLException {
        String query = "DELETE FROM contact_list WHERE id = ?";
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
