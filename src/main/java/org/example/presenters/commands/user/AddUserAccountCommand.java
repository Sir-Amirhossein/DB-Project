package org.example.presenters.commands.user;

import org.example.models.requests.UserAccount;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.user.UserCommand;
import org.example.presenters.services.UserAccountService;

import java.sql.SQLException;

public class AddUserAccountCommand implements UserCommand {
    private final UserAccount user;
    private final UserAccountService service;

    public AddUserAccountCommand(UserAccount user, UserAccountService service) {
        this.user = user;
        this.service = service;
    }

    @Override
    public ResponseCodes execute() throws SQLException {
        return this.service.addUserAccount(this.user);
    }
}
