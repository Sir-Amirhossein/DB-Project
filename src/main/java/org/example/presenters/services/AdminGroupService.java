package org.example.presenters.services;

import org.example.models.requests.AdminGroup;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.repository.AdminGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class AdminGroupService {
    @Autowired
    AdminGroupRepository adminGroupRepository;

    public ResponseCodes addAdminGroup (AdminGroup adminGroup) throws SQLException {
        adminGroupRepository.addAdminGroup(adminGroup);
        return ResponseCodes.SUCCESS;
    }

    public AdminGroup getAdminGroup (int id) throws SQLException {
        return this.adminGroupRepository.getAdminGroup(id);
    }

    public ResponseCodes updateAdminGroup (AdminGroup adminGroup) throws SQLException {
        return this.adminGroupRepository.updateAdminGroup(adminGroup);
    }

    public ResponseCodes deleteAdminGroup (int id) throws SQLException {
        return this.adminGroupRepository.deleteAdminGroup(id);
    }
}