package org.example.presenters.commands.admin;

import org.example.models.requests.AdminGroup;
import org.example.presenters.services.AdminGroupService;

public class UpdateAdminGroupCommand implements AdminGroupCommand {

    private final AdminGroup adminGroup;
    private final AdminGroupService service;

    public UpdateAdminGroupCommand(AdminGroup adminGroup, AdminGroupService service) {
        this.adminGroup = adminGroup;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.updateAdminGroup(adminGroup);
    }
}