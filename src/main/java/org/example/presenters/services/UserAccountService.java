package org.example.presenters.services;

import org.example.models.requests.Chat;
import org.example.models.requests.UserAccount;
import org.example.models.responses.ResponseCodes;
import org.example.models.responses.UserMessageCount;
import org.example.presenters.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository userAccountRepository;
    public ResponseCodes addUserAccount (UserAccount userAccount) throws SQLException {
        if (userAccountRepository.getUserAccount(userAccount.getUserId()) != null) {
            return ResponseCodes.DUPLICATED;
        }
        int resultSet = userAccountRepository.addUserAccount(userAccount);
        if (resultSet <= 0)
            return ResponseCodes.NOT_ALLOWED;
        return ResponseCodes.SUCCESS;
    }

    public UserAccount getUserAccount (int id) throws SQLException {
        return this.userAccountRepository.getUserAccount(id);
    }

    public ResponseCodes updateUserAccount (UserAccount userAccount) throws SQLException {
        return this.userAccountRepository.updateUserAccount(userAccount);
    }

    public ResponseCodes deleteUserAccount (int id) throws SQLException {
        return this.userAccountRepository.deleteUserAccount(id);
    }

    public List<Chat> getUserGroups (UserAccount userAccount) throws SQLException {
        return this.userAccountRepository.getUserChats(userAccount);
    }

    public List<UserAccount> getUsersInCommonGroups (int id) throws SQLException {
        return this.userAccountRepository.getUsersInCommonGroups(id);
    }

    public List<UserMessageCount> getUsersByMessageCount (int chat_id) throws SQLException {
        return this.userAccountRepository.getUsersByMessageCount(chat_id);
    }
}