package org.example.presenters.commands.user;

import org.example.presenters.services.UserAccountService;

public class DeleteUserAccountCommand implements UserCommand {

    private final int id;
    private final UserAccountService service;

    public DeleteUserAccountCommand(int id, UserAccountService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.deleteUserAccount(id);
    }
}
