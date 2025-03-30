package org.example.presenters.commands.admin;

import org.example.presenters.services.AdminGroupService;

public class SelectAdminGroupCommand implements AdminGroupCommand {

    private final int id;
    private final AdminGroupService service;

    public SelectAdminGroupCommand(int id, AdminGroupService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.getAdminGroup(id);
    }
}
