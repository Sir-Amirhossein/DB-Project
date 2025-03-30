package org.example.presenters.commands.members;

import org.example.presenters.services.GroupMembersService;

public class DeleteGroupMembersCommand implements GroupMembersCommand {

    private final int id;
    private final GroupMembersService service;

    public DeleteGroupMembersCommand(int id, GroupMembersService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.deleteGroupMembers(id);
    }
}
