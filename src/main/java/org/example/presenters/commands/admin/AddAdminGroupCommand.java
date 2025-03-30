package org.example.presenters.commands.admin;

import org.example.models.requests.AdminGroup;
import org.example.presenters.commands.admin.AdminGroupCommand;
import org.example.presenters.services.AdminGroupService;

public class AddAdminGroupCommand implements AdminGroupCommand {
    private final AdminGroup adminGroup;
    private final AdminGroupService service;

    public AddAdminGroupCommand(AdminGroup adminGroup, AdminGroupService service) {
        this.adminGroup = adminGroup;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.addAdminGroup(this.adminGroup);
    }
}
