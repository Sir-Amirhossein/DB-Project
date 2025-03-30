package org.example.presenters.commands.user;

import org.example.models.requests.UserAccount;
import org.example.presenters.services.UserAccountService;

public class UserGroupCommand implements UserCommand {

    private final UserAccount user;
    private final UserAccountService service;

    public UserGroupCommand(UserAccount user, UserAccountService service) {
        this.user = user;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.getUserGroups(user);
    }
}
