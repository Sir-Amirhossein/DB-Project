package org.example.presenters.commands.user;

import org.example.models.requests.UserAccount;
import org.example.presenters.services.UserAccountService;

public class UpdateUserAccountCommand implements UserCommand {

    private final UserAccount user;
    private final UserAccountService service;

    public UpdateUserAccountCommand(UserAccount user, UserAccountService service) {
        this.user = user;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.updateUserAccount(user);
    }
}
