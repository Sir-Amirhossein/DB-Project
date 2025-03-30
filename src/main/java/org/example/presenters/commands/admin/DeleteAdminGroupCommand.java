package org.example.presenters.commands.admin;

import org.example.presenters.services.AdminGroupService;

public class DeleteAdminGroupCommand implements AdminGroupCommand {

    private final int id;
    private final AdminGroupService service;

    public DeleteAdminGroupCommand(int id, AdminGroupService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.deleteAdminGroup(id);
    }
}
