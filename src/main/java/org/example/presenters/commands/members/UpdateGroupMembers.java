package org.example.presenters.commands.members;

import org.example.models.requests.GroupMembers;
import org.example.presenters.services.GroupMembersService;

public class UpdateGroupMembers implements GroupMembersCommand {

    private final GroupMembers groupMembers;
    private final GroupMembersService service;

    public UpdateGroupMembers(GroupMembers groupMembers, GroupMembersService service) {
        this.groupMembers = groupMembers;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.updateGroupMembers(groupMembers);
    }
}
