package org.example.presenters.repository;

import org.example.models.requests.Chat;
import org.example.models.requests.UserAccount;
import org.example.models.responses.ResponseCodes;
import org.example.models.responses.UserMessageCount;
import org.example.presenters.database.DatabaseConnector;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class UserAccountRepository {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_PATTERN = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
    private final DatabaseConnector connector = DatabaseConnector.getConnector();

    public int addUserAccount(UserAccount userAccount) throws SQLException {
        String query = "INSERT INTO user_account (first_name, last_name, username, phone_number, email) VALUES (?, ?, ?, ?, ?)";
        if (!isValidEmail(userAccount.getEmail()) || !isValidPhoneNumber(userAccount.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid email or phone number");
        }
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userAccount.getFirstName());
            statement.setString(2, userAccount.getLastName());
            statement.setString(3, userAccount.getUserName());
            statement.setString(4, userAccount.getPhoneNumber());
            statement.setString(5, userAccount.getEmail());
            return statement.executeUpdate();
        }
    }

    public UserAccount getUserAccount(int user_id) throws SQLException {
        String query = "SELECT * FROM user_account WHERE user_id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createUserAccount(resultSet);
                } else {
                    return null;
                }
            }
        }
    }

    public ResponseCodes updateUserAccount(UserAccount userAccount) throws SQLException {
        String query = "UPDATE user_account SET first_name=?, last_name=?, username=?, phone_number=?, " +
                "email=? WHERE user_id=?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userAccount.getFirstName());
            statement.setString(2, userAccount.getLastName());
            statement.setString(3, userAccount.getUserName());
            statement.setString(4, userAccount.getPhoneNumber());
            statement.setString(5, userAccount.getEmail());
            statement.setInt(6, userAccount.getUserId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0)
                return ResponseCodes.NOT_UPDATED;
            return ResponseCodes.SUCCESS;
        }
    }

    public ResponseCodes deleteUserAccount(int user_id) throws SQLException {
        String query = "DELETE FROM user_account WHERE user_id = ?";
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0)
                return ResponseCodes.SUCCESS;
            return ResponseCodes.NOT_DELETED;
        }
    }

    public List<Chat> getUserChats (UserAccount userAccount) throws SQLException {
        String query = "SELECT * FROM chat WHERE chat_id in (SELECT chat_id FROM group_members WHERE user_id=?) AND " +
                "is_group_chat=true";
        List<Chat> chats = new ArrayList<>();
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userAccount.getUserId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Chat chat = new Chat();
                chat.setChatId(resultSet.getInt("chat_id"));
                chat.setGroupChat(resultSet.getBoolean("is_group_chat"));
                chat.setTimestamp(resultSet.getTimestamp("timestamp"));
                chats.add(chat);
            }
            return chats;
        }
    }

    /**
     * این کوئری ابتدا شناسه‌های گروه‌هایی که کاربر خاصی عضو آن‌ها است را پیدا می‌کند.
     * سپس همه کاربرانی که در این گروه‌ها عضو هستند (به جز کاربر خاص) را پیدا می‌کند.
     */
    public List<UserAccount> getUsersInCommonGroups(int specificUserId) throws SQLException {
        String query = "SELECT * FROM user_account WHERE user_id IN (" +
                "SELECT user_id FROM group_members WHERE chat_id IN (" +
                "SELECT chat_id FROM group_members NATURAL JOIN chat WHERE is_group_chat=true AND" +
                " user_id=?)) AND user_id != ?";
        List<UserAccount> userAccounts = new ArrayList<>();
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, specificUserId);
            statement.setInt(2, specificUserId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userAccounts.add(createUserAccount(resultSet));
                }
            }
        }
        return userAccounts;
    }

    private UserAccount createUserAccount(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(resultSet.getInt("user_id"));
        userAccount.setFirstName(resultSet.getString("first_name"));
        userAccount.setLastName(resultSet.getString("last_name"));
        userAccount.setUserName(resultSet.getString("username"));
        userAccount.setPhoneNumber(resultSet.getString("phone_number"));
        userAccount.setEmail(resultSet.getString("email"));
        return userAccount;
    }

    public List<UserMessageCount> getUsersByMessageCount(int chatId) throws SQLException {
        String query = "SELECT ua.user_id, ua.first_name, ua.last_name, ua.username, ua.phone_number, ua.email, COUNT(m.message_id) as message_count " +
                "FROM user_account ua " +
                "JOIN message m ON ua.user_id = m.sender_id " +
                "WHERE m.chat_id = ? " +
                "GROUP BY ua.user_id, ua.first_name, ua.last_name, ua.username, ua.phone_number, ua.email " +
                "ORDER BY message_count DESC";

        List<UserMessageCount> userMessageCounts = new ArrayList<>();
        try (Connection connection = connector.startConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, chatId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UserMessageCount userMessageCount = new UserMessageCount();
                    userMessageCount.setUserId(resultSet.getInt("user_id"));
                    userMessageCount.setFirstName(resultSet.getString("first_name"));
                    userMessageCount.setLastName(resultSet.getString("last_name"));
                    userMessageCount.setUserName(resultSet.getString("username"));
                    userMessageCount.setPhoneNumber(resultSet.getString("phone_number"));
                    userMessageCount.setEmail(resultSet.getString("email"));
                    userMessageCount.setMessageCount(resultSet.getInt("message_count"));
                    userMessageCounts.add(userMessageCount);
                }
            }
        }
        return userMessageCounts;
    }
    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


}
