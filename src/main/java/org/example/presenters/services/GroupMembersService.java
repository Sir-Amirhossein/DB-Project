package org.example.presenters.services;

import org.example.models.requests.GroupMembers;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.repository.GroupMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class GroupMembersService {
    @Autowired
    private GroupMembersRepository groupMembersRepository;

    public ResponseCodes addMembers (GroupMembers groupMembers) throws SQLException {
        groupMembersRepository.addGroupMembers(groupMembers);
        return ResponseCodes.SUCCESS;
    }

    public GroupMembers getMembers (int id) throws SQLException {
        return this.groupMembersRepository.getGroupMembers(id);
    }

    public ResponseCodes updateGroupMembers (GroupMembers groupMembers) throws SQLException {
        return this.groupMembersRepository.updateGroupMembers(groupMembers);
    }

    public ResponseCodes deleteGroupMembers (int id) throws SQLException {
        return this.groupMembersRepository.deleteGroupMembers(id);
    }
}